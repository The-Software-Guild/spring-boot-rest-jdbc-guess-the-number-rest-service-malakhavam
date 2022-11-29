package mmala.guessthenumber.controller;

import mmala.guessthenumber.models.Game;
import mmala.guessthenumber.models.Round;
import mmala.guessthenumber.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api") 
public class Controller {
    @Autowired
    GameService service;
    
    
    @PostMapping("/begin") //  http://localhost:8080/api/begin
    @ResponseStatus(HttpStatus.CREATED) //return a 201 CREATED message
    
    // Starts a game, generates an answer, and sets the correct status
    public int createGame() {
        // returns created gameId
        return service.newGame();
    }
    
     @PostMapping("/guess") // http://localhost:8080/api/guess
     // do not forget to input JSON body
    public Round makeGuess(@RequestBody Round round) {
        return service.makeGuess(round);   
    }
    
    @GetMapping("/game") // http://localhost:8080/api/game
    public List<Game> getAllGames() {
        return service.getAllGames();
    }
    
    @GetMapping("/game/{game_id}") // http://localhost:8080/api/game/{game_id}
    public Game getGameById(@PathVariable("game_id") int gameId) {
        return service.getGameById(gameId);
    }
    
    @GetMapping("/rounds/{game_id}") // http://localhost:8080/api/rounds/{game_id}
    public List<Round> getRoundsForGame(@PathVariable("game_id") int gameId) {
        return service.getAllRoundsByGameId(gameId);
    }
  
}
