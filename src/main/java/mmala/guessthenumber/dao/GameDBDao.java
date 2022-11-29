package mmala.guessthenumber.dao;

import mmala.guessthenumber.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class GameDBDao implements GameDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    
    // Returns a list of all games. Be sure in-progress games do not display their answer.
    public List<Game> getAllGames() {
        final String SQL = "SELECT * FROM game";
        return jdbc.query(SQL, new GameMapper());
    }

    @Override
    
    // Returns a specific game based on ID. Be sure in-progress games do not display their answer.
    public Game getGameById(int gameId) {
        try {
            final String SQL = "SELECT * FROM game WHERE game_id = ?";
            return jdbc.queryForObject(SQL, new GameMapper(), gameId);
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    
     @Override
    public boolean deleteById(int gameId) {
        
        final String SQL = "DELETE FROM game WHERE game_id = ?;";
        return jdbc.update(SQL , gameId) > 0;
    }


    @Override
    @Transactional
    public Game addGame(Game game) {
        final String SQL = "INSERT INTO game(answer) VALUES (?)";
        jdbc.update(SQL, game.getAnswer());
        
        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
    }
    
    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET finished = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME, game.isFinished(), game.getGameId());
    }
    
    
    
    public static final class GameMapper implements RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }
    
}
