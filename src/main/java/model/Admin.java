package model;

public class Admin extends Employee {

	public Admin(int uid, Credentials credentials, String employeeName) {
		super(uid, credentials, employeeName);
	}
	
	public void deleteAccount(Account account) {
		
	}
	
	public Account accessAccount(String username) {
		return null;
	}
	
	public void createAccount() {
		
	}

}
