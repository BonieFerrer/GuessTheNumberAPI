/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.data;

import bonie.guessthenumberapi.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static junit.runner.Version.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bonie_TSG
 */
/* The @Repository annotation is a class-level annotation 
that tells Spring this is an injectable bean. */
@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    /* display all the game created from table game */
    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE game_id = ?";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /*Transactional annotation - @Transactional to wrap a method in a database transaction. 
    It allows us to set propagation, isolation, timeout, read-only, 
    and rollback conditions for our transaction. */
     /*create and generate  new game and add it into game table*/
    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer) VALUES (?)";
        jdbc.update(INSERT_GAME, game.getAnswer());

        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
    }

    /*update game and game table if the player guess the correct number */
    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET finished = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME, game.isFinished(), game.getGameId());
    }

    /*Delete gameId from table game*/
    @Override
    public boolean deleteGameById(int gameId) {
     
        final String DELETE_GAME_BY_GAME = "DELETE g.* FROM game g  "
                + "JOIN round r ON g.game_Id = r.game_Id WHERE r.game_Id = ?";
        jdbc.update(DELETE_GAME_BY_GAME, gameId);
        
        final String DELETE_GAME_BY_ROUND = "DELETE FROM round WHERE game_Id = ?";
        jdbc.update(DELETE_GAME_BY_ROUND, gameId);
        
        final String DELETE_GAME = "DELETE FROM game WHERE game_Id = ?";
        jdbc.update(DELETE_GAME, gameId);
        return false;
     

    }
    
 /*   
    final String DELETE_MEETING_EMPLOYEE_BY_ROOM = "DELETE me.* FROM meeting_employee me "
                + "JOIN meeting m ON me.meetingId = m.id WHERE m.roomId = ?";
        jdbc.update(DELETE_MEETING_EMPLOYEE_BY_ROOM, id);
        
        final String DELETE_MEETING_BY_ROOM = "DELETE FROM meeting WHERE roomId = ?";
        jdbc.update(DELETE_MEETING_BY_ROOM, id);
        
        final String DELETE_ROOM = "DELETE FROM room WHERE id = ?";
        jdbc.update(DELETE_ROOM, id);
*/
    
    
    /* RowMapper saves a lot of code becuase it internally adds the data of ResultSet into the collection.*/
    /*RowMapper interface allows to map a row of the relations with the instance of user-defined class
    " game_Id, answer,finished". It iterates the ResultSet internally and adds it into the collection.
    So we don't need to write a lot of code to fetch the records as ResultSetExtractor. */
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
