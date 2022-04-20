package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionUtil;

public class Game {
	
	private int uid;
	private String name;
	private BigDecimal price;
	
	public Game(int uid, String name, BigDecimal price) {
		this.uid = uid;
		this.name = name;
		this.price = price;
	}
	
	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public String confirm() {
		return ("Try your hand at " + name + " for $" + price + "? (Y/N)");
	}
	
	public void play(Customer customer) {
		System.out.println("Playing " + name + "...");
		BigDecimal startingWallet = customer.getWalletBalance();
		BigDecimal resultingWallet = startingWallet.subtract(price);
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("UPDATE balances SET account_wallet = ? WHERE account_uid = ?");
			statement.setBigDecimal(1, resultingWallet);
			statement.setInt(2, customer.getUid());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	public void getResults(Customer customer) {
		
		int tickets = (int)(Math.random() * 11);
		
		if(tickets == 0) {
			System.out.println("You did horribly. You didn't get a single ticket.");
		} else if (tickets >= 1 && tickets <= 3) {
			System.out.println("You did poorly. You only got " + tickets + " tickets."); 
		} else if (tickets >= 4 && tickets <=6) {
			System.out.println("You did an average job. You got " + tickets + " tickets."); 
		} else if (tickets >= 7 && tickets <= 9) {
			System.out.println("You did a good job. You got " + tickets + " tickets."); 
		} else if (tickets == 10) {
			System.out.println("You did amazing! You got ten tickets."); 
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("UPDATE balances SET account_tickets = account_tickets + ? WHERE account_uid = ?");
			statement.setInt(1, tickets);
			statement.setInt(2, customer.getUid());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
		
	}
	
}
