/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.data;

import bonie.guessthenumberapi.TestApplicationConfiguration;
import bonie.guessthenumberapi.models.Game;
import bonie.guessthenumberapi.models.Round;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Bonie_TSG
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoDBTest {
    
    
     @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    
    public RoundDaoDBTest() {
    }
    
  
    /* This is not works properly--from here-- */
    
   @After
    public  void teardown() {
        List<Game> games = gameDao.getAllGames();
        games.forEach(game -> {
            gameDao.deleteGameById(game.getGameId());
         });
      
      List<Round> rounds = roundDao.getAllRoundsByGameId(0);
      rounds.forEach(round -> {
          roundDao.deleteByRound(round.getRoundId());
         }); 
       
}
    /*--to here-- need to setup properly */
    
    
    @Test
    public void testAddGetAllRound() {
   
    
    int gameId = 1;
        
        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setGameId(gameId);
        roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGuess("5678");
        round2.setResult("e:4:p:0");
        round2.setGameId(gameId);
        roundDao.addRound(round2);

        
        List<Round> rounds = roundDao.getAllRoundsByGameId(gameId);

        assertEquals(2, rounds.size());
        assertNotNull(round = roundDao.getRoundById(round.getRoundId()));
    }
    
} 
    

