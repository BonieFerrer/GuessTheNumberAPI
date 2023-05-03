/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonie.guessthenumberapi.service;

import bonie.guessthenumberapi.data.GameDao;
import bonie.guessthenumberapi.data.RoundDao;
import bonie.guessthenumberapi.models.Game;
import bonie.guessthenumberapi.models.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bonie_TSG
 */
@Service
public class ServiceLayer {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    /* display all games and hide the answer with * if the  exact answer is not guessed */
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        games.stream().filter(game -> (!game.isFinished())).forEachOrdered(game -> {
            game.setAnswer("****");
        });

        return games;
    }

    /* display all games and rounds by game Id */
    public List<Round> getAllRoundsByGameId(int gameId) {
        return roundDao.getAllRoundsByGameId(gameId);
    }

    /* accept and update the answer from player, the game calculate  if
       the answer is true or false . */
    public Round makeGuess(Round round) {
        String answer = gameDao.getGameById(round.getGameId()).getAnswer();
        String guess = round.getGuess();
        String result = determineResult(guess, answer);
        round.setResult(result);

        if (guess.equals(answer)) {
            Game game = getGameById(round.getGameId());
            game.setFinished(true);
            gameDao.updateGame(game);
        }

        return roundDao.addRound(round);
    }

    /* read and display game using gameId, if game is not finished hide the answer with */
    public Game getGameById(int gameId) {
        Game game = gameDao.getGameById(gameId);
        if (!game.isFinished()) {
            game.setAnswer("****");
        }

        return game;
    }

    /* Create a new game from player */
    public Game addGame(Game game) {
        return gameDao.addGame(game);
    }

    /* Create and add new game with random generated  (4) digit numbers from 0-9. */
    public int newGame() {
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game = gameDao.addGame(game);

        return game.getGameId();
    }

    private String generateAnswer() {
        Random rnd = new Random();
        int digit1 = rnd.nextInt(10);

        int digit2 = rnd.nextInt(10);
        while (digit2 == digit1) {
            digit2 = rnd.nextInt(10);
        }

        int digit3 = rnd.nextInt(10);
        while (digit3 == digit2 || digit3 == digit1) {
            digit3 = rnd.nextInt(10);
        }

        int digit4 = rnd.nextInt(10);
        while (digit4 == digit3 || digit4 == digit2 || digit4 == digit1) {
            digit4 = rnd.nextInt(10);
        }

        String answer = String.valueOf(digit1) + String.valueOf(digit2)
                + String.valueOf(digit3) + String.valueOf(digit4);

        return answer;
    }

    /*read and calculate the player answer by comparing guess and answer*/
    /*update game and round after answer being calculated*/
    public String determineResult(String guess, String answer) {
        char[] guessChars = guess.toCharArray();
        char[] answerChars = answer.toCharArray();
        int exact = 0;
        int partial = 0;

        for (int i = 0; i < guessChars.length; i++) {
            // -1 indicates that index value of guessChars DNE in answer
            // otherwise the number must be in the string. Then check where
            if (answer.indexOf(guessChars[i]) > -1) {
                if (guessChars[i] == answerChars[i]) {
                    exact++;
                } else {
                    partial++;
                }
            }
        }

        String result = "e:" + exact + ":p:" + partial;

        return result;
    }

}
