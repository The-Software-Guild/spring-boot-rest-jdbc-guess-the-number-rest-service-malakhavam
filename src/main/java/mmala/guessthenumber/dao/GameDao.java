package mmala.guessthenumber.dao;

import mmala.guessthenumber.models.Game;
import java.util.List;


public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(Game game);
    void updateGame(Game round);
    // true if the item exists and is updated
    boolean deleteById(int id);
   
   
}
