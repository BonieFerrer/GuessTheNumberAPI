/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.data;

import bonie.guessthenumberapi.models.Round;
import java.util.List;

/**
 *
 * @author Bonie_TSG
 */
public interface RoundDao {
    
    List<Round> getAllRoundsByGameId(int gameId);
    Round getRoundById(int roundId);
    Round addRound(Round round);
    boolean deleteByRound(int roundId);
   /* boolean deleteGameIdOnRound(int gameId);*/
    
  
    
}
