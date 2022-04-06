package model;

import java.math.BigDecimal;

public class Account {
	private Address address;
	private User mainUser;
	private User[] users;
	private BigDecimal walletBalance;
	private int ticketBalance;
}
