/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.data;

import bonie.guessthenumberapi.models.Game;
import java.util.List;

/**
 *
 * @author Bonie_TSG
 */
public interface GameDao {
    
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(Game game);
    void updateGame(Game round);
    boolean deleteGameById(int gameId);
    
}
