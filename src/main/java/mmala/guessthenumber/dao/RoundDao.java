
package mmala.guessthenumber.dao;

import mmala.guessthenumber.models.Game;
import mmala.guessthenumber.models.Round;
import java.util.List;


public interface RoundDao {
    
    List<Round> getAllRoundsByGameId(int gameId);
    List<Round> getAll();
    Round getRoundById(int roundId);
    Round addRound(Round round);
    // true if item exists and is updated
    boolean update(Round round);

    // true if item exists and is deleted
    boolean deleteById(int id);
        
}
