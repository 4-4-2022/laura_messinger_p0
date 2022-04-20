package model;

import java.math.BigDecimal;

public class Account {
	
	private int uid;
	private Credentials credentials;
	
	public Account(int uid, Credentials credentials) {
		this.uid = uid;
		this.credentials = credentials;
	}

	public Credentials getCredentials() {
		return credentials;
	}
	
	public int getUid() {
		return uid;
	}
	
}
