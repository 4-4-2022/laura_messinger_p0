package model;

import java.math.BigDecimal;

public class Account {
	private Credentials credentials;
	private Address address;
	private User[] users;
	private BigDecimal walletBalance;
	private int ticketBalance;	
	
	public Account(Credentials credentials, Address address, User[] users, BigDecimal walletBalance,
			int ticketBalance) {
		super();
		this.credentials = credentials;
		this.address = address;
		this.users = users;
		this.walletBalance = walletBalance;
		this.ticketBalance = ticketBalance;
	}

	public boolean validAccount(Credentials credentials) {
		return false;
	}
	
}
