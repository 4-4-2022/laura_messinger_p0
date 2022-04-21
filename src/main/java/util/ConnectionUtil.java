package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(System.getenv("p0url"), System.getenv("p0username"), System.getenv("p0password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeStatement(Statement statement) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void closeResultSet(ResultSet results) {
		try {
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
