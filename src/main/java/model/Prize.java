package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.ConnectionUtil;

public class Prize {
	
	private int uid;
	private String name;
	private int cost;
	
	public Prize(int uid, String name, int cost) {
		this.uid = uid;
		this.name = name;
		this.cost = cost;
	}
	
	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public String confirm(Customer customer) {
		return ("Redeem " + cost + " tickets for " + name + "? It will be shipped to " + customer.getAddress().formatAddress() + ". Y/N");
	}
	
	public void deductTickets(Customer customer) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("UPDATE balances SET account_tickets = account_tickets - ? WHERE account_uid = ?");
			statement.setInt(1, cost);
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
