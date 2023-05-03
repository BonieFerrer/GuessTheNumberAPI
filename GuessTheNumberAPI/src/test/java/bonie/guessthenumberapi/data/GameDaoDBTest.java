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
public class GameDaoDBTest {
    
    @Autowired 
    GameDao gameDao;
    
    @Autowired 
    RoundDao roundDao;
    
    public GameDaoDBTest() {
    }
    
  
    /**
     * Test of getAllGames method, of class GameDaoDB.
     */
    /* this test add 2 set of numbers in database
    and test if  the numbers been added, and  it should be equal into
    2 numbers been save on the database */
    
    
    
  
    @Before
    
    public  void setup() {
        List<Game> games = gameDao.getAllGames();
        games.forEach(game -> {
            gameDao.deleteGameById(game.getGameId());
         });
      
      List<Round> rounds = roundDao.getAllRoundsByGameId(0);
      rounds.forEach(round -> {
          roundDao.deleteByRound(round.getRoundId());
         });
       
    }
    
   /* @After
    public  void teardown(){
         List<Game> games = gameDao.getAllGames();
        games.forEach(game -> {
            gameDao.deleteGameById(game.getGameId());
         });
      
      List<Round> rounds = roundDao.getAllRoundsByGameId(0);
      rounds.forEach(round -> {
          roundDao.deleteByRound(round.getRoundId());
         }); 
       
    
}*/
    /**
     * Test of AddGetAllGames method, of class GameDaoDB.
     */
    
     @Test
     public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setFinished(false);
        gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2)); 
        
       
        
    }
     

    /**
     * Test of AddGetGame method, of class GameDaoDB.
     */
    
    @Test
    public void testAddGetGamebyId() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao); 
       
        
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

        game.setFinished(true);

        gameDao.updateGame(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }
        
        
        
        
        
        
    }
    

