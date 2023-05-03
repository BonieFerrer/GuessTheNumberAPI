/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.controller;

import bonie.guessthenumberapi.models.Game;
import bonie.guessthenumberapi.models.Round;
import bonie.guessthenumberapi.service.ServiceLayer;
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

/**
 *
 * @author Bonie_TSG
 */


    /*@PostMapping enables our method to accept POST requests for the path "/api/begin". */
    /*@ResponseStatus(HttpStatus.CREATED) sets the HTTP status code to 204 Created */

    /*@RestController does three things:
      1.Makes our class injectable. It will be injected into Spring MVC core dependents.
      2.Tells Spring MVC to scan for methods that can handle HTTP requests.
      3.Tells Spring MVC to convert method results to JSON.*/

    @RestController
    @RequestMapping("/api")
    public class Controller {
    
    @Autowired
    ServiceLayer service;
     
 
 
    /*@PostMapping enables our method to accept POST requests for the path "/api/begin". */
    /*@ResponseStatus(HttpStatus.CREATED) sets the HTTP status code to 204 Created */
 
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {
        return service.newGame();
    }
    
   /*@PostMapping enables our method to accept POST requests for the path "/api/begin". */
    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round round) {
        return service.makeGuess(round);   
    }
    
    
    /*The all method is annotated with @GetMapping. That means it will be activated for
    GET requests for the path "/api/game". It's returned type, 
    List<ToDo> will be serialized to JSON in the HTTP response body.*/
    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }
    
    /*The all method is annotated with @GetMapping. That means it will be activated for
    GET requests for the path "/api/game/{game_id}". It's returned type, 
    List<ToDo> will be serialized to JSON in the HTTP response body.*/
    @GetMapping("/game/{game_id}")
    public Game getGameById(@PathVariable("game_id") int gameId) {
        return service.getGameById(gameId);
    }
    
    /*The all method is annotated with @GetMapping. That means it will be activated for
    GET requests for the path "/api/rounds/{game_id}". It's returned type, 
    List<ToDo> will be serialized to JSON in the HTTP response body.*/
    @GetMapping("/rounds/{game_id}")
    public List<Round> getRoundsForGame(@PathVariable("game_id") int gameId) {
        return service.getAllRoundsByGameId(gameId);
    }
     
    
    
}
