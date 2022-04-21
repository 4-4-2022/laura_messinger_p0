package model;

public class Employee extends Account {
	
	private String employeeName;

	public Employee(int uid, Credentials credentials, String employeeName) {
		super(uid, credentials);
		this.employeeName = employeeName;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public Account lookupAccount(String username) {
		return null;
	}
	
	public void createEmployee() {
		
	}

}
