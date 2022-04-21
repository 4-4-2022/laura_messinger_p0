/* this script populates and returns the table of available games */

DROP TABLE IF EXISTS games;
CREATE TABLE IF NOT EXISTS games(
	game_uid SERIAL PRIMARY KEY,
	game_name TEXT UNIQUE NOT NULL,
	game_cost MONEY NOT NULL
);

INSERT INTO games VALUES
(DEFAULT, 'Duck Shooter', 1.50),
(DEFAULT, 'Pinball', 1.00),
(DEFAULT, 'Space Fighter', 0.75),
(DEFAULT, 'Jousting', 1.00),
(DEFAULT, 'Frog Hop', 0.50),
(DEFAULT, 'Riddlez', 1.25);

SELECT * FROM games;