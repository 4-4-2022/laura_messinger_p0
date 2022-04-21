/*on average, $1 worth of game-playing should get you 5 tickets, so that's kind of my exchange rate*/

DROP TABLE IF EXISTS prizes;
CREATE TABLE IF NOT EXISTS prizes(
	prize_uid SERIAL PRIMARY KEY,
	prize_name TEXT UNIQUE NOT NULL,
	prize_cost INTEGER NOT NULL
);

INSERT INTO prizes VALUES
(DEFAULT, 'Skateboard', 250),
(DEFAULT, 'Inflatable Alien', 30),
(DEFAULT, 'Foam Dart Gun', 100),
(DEFAULT, 'Digital Watch', 50),
(DEFAULT, 'Soccer Ball', 50),
(DEFAULT, 'Cute Plush Toy', 75),
(DEFAULT, 'Colored Markers', 15),
(DEFAULT, 'Lava Lamp', 100),
(DEFAULT, 'Keychain Charm', 10),
(DEFAULT, 'Rainbow Bracelet', 10);

SELECT * FROM prizes;