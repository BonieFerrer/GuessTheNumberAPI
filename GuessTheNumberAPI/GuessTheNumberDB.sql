DROP DATABASE IF EXISTS GuessTheNumberDB;
CREATE DATABASE GuessTheNumberDB;
USE GuessTheNumberDB;

CREATE TABLE game (
	game_Id INT PRIMARY KEY AUTO_INCREMENT,
    answer char(4),
    finished BOOLEAN DEFAULT false
    );
    
INSERT INTO game(game_id, answer, finished) VALUES
	(1, "2971", true),
	(2, "9218", true),
	(3, "2345", true);

CREATE TABLE round (
	round_Id INT PRIMARY KEY AUTO_INCREMENT,
    game_id INT NOT NULL,
    guess_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    guess CHAR(4),
    result CHAR(7),
    FOREIGN KEY fk_gameid (game_id) REFERENCES game(game_id)
    );
    
    INSERT INTO round (round_Id, game_Id, guess_time, guess, result) VALUES 
    (1, 1, "2023-02-03 07:25:18", "2349", "e:1:p:1"),
    (2, 1, "2023-02-02 09:11:07", "9186", "e:0:p:2"),
    (3, 1, "2023-02-04 09:11:49", "2971", "e:4:p:0"),
    (4, 2, "2023-02-12 17:31:09", "9218", "e:4:p:0"),
    (5, 3, "2023-02-16 02:42:55", "1678", "e:0:p:0"),
    (6, 3, "2023-02-26 02:43:12", "2345", "e:4:p:0");
    
	select * from game;
    select * from round
    
    
    