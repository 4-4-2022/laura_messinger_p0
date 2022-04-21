package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionUtil;

public class Customer extends Account{
	
	private Address address;
	private List<User> users;
	private BigDecimal walletBalance;
	private int ticketBalance;
	
	public Customer(int uid, Credentials credentials) {
		super(uid, credentials);
	}

	public Address getAddress() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT * FROM addresses WHERE account_uid = ?");
			statement.setInt(1, getUid());
			results = statement.executeQuery();
			while(results.next()) {
				return new Address(results.getString("account_street"), results.getString("account_city"), results.getString("account_state"), results.getString("account_zip"), results.getString("account_country"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		return null;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<User> getUsers() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		List<User> users = new ArrayList<>();
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT * FROM users WHERE account_uid = ?");
			statement.setInt(1, getUid());
			results = statement.executeQuery();
			while(results.next()) {
				users.add(new User(results.getString("user_nickname"), results.getBoolean("user_isowner")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		return users;
	}

	public void setUsers(List<User>users) {
		this.users = users;
	}

	public BigDecimal getWalletBalance() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		BigDecimal walletBalance = new BigDecimal(0);
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT account_wallet FROM balances WHERE account_uid = ?");
			statement.setInt(1, getUid());
			results = statement.executeQuery();
			while(results.next()) {
				walletBalance = results.getBigDecimal("account_wallet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		return walletBalance;
	}

	public void setWalletBalance(BigDecimal amountToAdd) {
		Connection connection = null;
		PreparedStatement statement = null;
		BigDecimal finalBalance = getWalletBalance().add(amountToAdd);
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("UPDATE balances SET account_wallet = ? WHERE account_uid = ?");
			statement.setBigDecimal(1, finalBalance);
			statement.setInt(2, getUid());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}

	public int getTicketBalance() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		int ticketBalance = 0;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT account_tickets FROM balances WHERE account_uid = ?");
			statement.setInt(1, getUid());
			results = statement.executeQuery();
			while(results.next()) {
				ticketBalance = results.getInt("account_tickets");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		return ticketBalance;
	}

	public void setTicketBalance(int ticketBalance) {
		this.ticketBalance = ticketBalance;
	}
}
