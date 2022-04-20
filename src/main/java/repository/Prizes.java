package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Prize;
import util.ConnectionUtil;

public class Prizes {
	public static List<Prize> getPrizes(){
		
		List<Prize> prizes = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT * FROM prizes");
			while(results.next()) {
				prizes.add(new Prize(results.getInt("prize_uid"), results.getString("prize_name"), results.getInt("prize_cost")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		
		return prizes;
	}
}
