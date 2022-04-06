package repository;

import java.math.BigDecimal;

import model.Account;
import model.Address;
import model.Credentials;
import model.User;

public class Accounts {
	
	public static Account[] accounts = {
			new Account(
					new Credentials("username", "password"),
					new Address("123 Road St", "City", "State", "12345", "USA"),
					new User[]{
							new User("Mom", true),
							new User("Junior", false)
					},
					new BigDecimal(10.25),
					5
			),
			new Account(
					new Credentials("xXuserXx", "idontknow"),
					new Address("321 Street Rd", "City", "State", "12345", "USA"),
					new User[]{
							new User("Dad", true),
							new User("Junior", false)
					},
					new BigDecimal(6.25),
					0
			)
	};
	
}
