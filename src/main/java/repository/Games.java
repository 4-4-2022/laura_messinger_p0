package repository;

import java.math.BigDecimal;

import model.Game;

public class Games {

	public static Game[] games = {
			new Game("Duck Shooter", new BigDecimal(2.00)),
			new Game("Pinball", new BigDecimal(1.50)),
			new Game("Space Fighter", new BigDecimal(0.50)),
			new Game("Jousting", new BigDecimal(1.00)),
			new Game("Frog Hop", new BigDecimal(0.75))
	};
}
