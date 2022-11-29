
package mmala.guessthenumber.dao;

import mmala.guessthenumber.models.Game;
import mmala.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class RoundDBDao implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    // Returns a list of rounds for the specified game 
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
        final String SQL = "SELECT * FROM round "
                + "WHERE game_id = ? ORDER BY guess_time"; // sorted by time
        List<Round> rounds = jdbc.query(SQL, new RoundMapper(), gameId);
        return rounds;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
     @Override
    public List<Round> getAll() {
        //implement
        final String sql= "SELECT round_Id, game_Id, guess_time, guess, result"
        + " FROM round";
        return jdbc.query(sql, new RoundMapper());
    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SQL = "SELECT * FROM round WHERE round_Id = ?";
            return jdbc.queryForObject(SQL, new RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }    
    }
    
     @Override
    public boolean update(Round round) {
        return false;
    }

     @Override
    public boolean deleteById(int roundId) {
        return jdbc.update("DELETE FROM round WHERE round_Id = ?", roundId) > 0;
    }

    
    @Override
    @Transactional
    public Round addRound(Round round) {
        final String SQL = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(SQL, round.getGameId(), round.getGuess(), round.getResult());
        
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return getRoundById(newRoundId);
    }
    
       
    
    public static final class RoundMapper implements RowMapper<Round> {
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("round_id"));
            round.setGameId(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));
            
            Timestamp timestamp = rs.getTimestamp("guess_time");
            round.setGuessTime(timestamp.toLocalDateTime());
            
            round.setResult(rs.getString("result"));
            return round;
        }
    }
   
}
