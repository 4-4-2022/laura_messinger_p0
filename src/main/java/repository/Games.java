package repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import util.ConnectionUtil;

public class Games {
	
	public static List<Game> getGames(){
		
		List<Game> games = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT * FROM games");
			while(results.next()) {
				games.add(new Game(results.getInt("game_uid"), results.getString("game_name"), results.getBigDecimal("game_cost")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		
		return games;
	}
	
}
