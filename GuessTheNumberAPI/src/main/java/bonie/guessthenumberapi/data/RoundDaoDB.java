/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.data;

import bonie.guessthenumberapi.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

/*The @Repository annotation is a class-level annotation 
that tells Spring this is an injectable bean. */
@Repository
public class RoundDaoDB implements RoundDao{
    
    
    @Autowired
    JdbcTemplate jdbc;

    /*Read and display all rounds by gameId from table round */
    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                    + "WHERE game_id = ? ORDER BY guess_time";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /*Read and display roundId from table round */
    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /*Transactional annotation - @Transactional to wrap a method in a database transaction. 
    It allows us to set propagation, isolation, timeout, read-only, 
    and rollback conditions for our transaction. */
    /**
     *
     * @param round
     * @return
     */
    /*update table round data by inserting some values from the player guess*/
    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameId(), round.getGuess(), round.getResult());

        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return getRoundById(newRoundId);
    }

   /*deleting roundId from table round*/
 /*This not in use */
    @Override
    @Transactional
    public boolean deleteByRound(int roundId) {
        
        final String DELETE_GAME_BY_ROUND = "DELETE FROM round WHERE game_Id = ?";
        jdbc.update(DELETE_GAME_BY_ROUND, roundId);
        
        final String DELETE_GAME = "DELETE FROM game WHERE game_Id = ?";
        jdbc.update(DELETE_GAME, roundId);
        return false;
             
            
    }

   

 /* RowMapper saves a lot of code becuase it internally adds the data of ResultSet into the collection.*/
 /*RowMapper interface allows to map a row of the relations with the instance of user-defined class
   " roundId, game_Id, guess, guess_time,result". It iterates the ResultSet internally and adds it into the collection.
   So we don't need to write a lot of code to fetch the records as ResultSetExtractor. */
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
