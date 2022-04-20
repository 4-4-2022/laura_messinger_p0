package app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Account;
import model.Admin;
import model.Credentials;
import model.Customer;
import model.Employee;
import model.Game;
import model.Prize;
import model.User;
import repository.Games;
import repository.Prizes;
import util.ConnectionUtil;

public class Client {
	
	private static void printAbout(Scanner scanner) {
		System.out.println("The Online Arcade works just like a regular arcade or carnival.");
		System.out.println("You can add some money to your digital wallet, and pay a little to play a game.");
		System.out.println("If you do really well at the game, you'll earn a lot of tickets.");
		System.out.println("Tickets can be redeemed for real gifts that we'll mail to you!");
		System.out.println("1) Go back");
		if(scanner.nextLine().equals("1")) {
			startUI(scanner);
		}
	}
	
	private static void getUsername(Scanner scanner) {
		System.out.println("Please enter your username.");
		String username = scanner.nextLine();
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		List<String> usernames = new ArrayList<>();
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT account_username FROM credentials");
			while(results.next()) {
				usernames.add(new String(results.getString("account_username")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		if(usernames.contains(username)) {
			System.out.println("Account found.");
			getPassword(scanner, username);
		} else {
			System.out.println("Account not found.");
			getUsername(scanner);
		}

	}
	
	private static void getPassword(Scanner scanner, String username) {
		System.out.println("Please enter your password.");
		String password = scanner.nextLine();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String correctPassword = null;
		int uid = 0;
		String email = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT * FROM credentials WHERE account_username = ?");
			statement.setString(1, username);
			results = statement.executeQuery();
			while(results.next()) {
				correctPassword = results.getString("account_password");
				email = results.getString("account_email");
				uid = results.getInt("account_uid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		if(password.equals(correctPassword)) {
			System.out.println("Password correct.");
			if (getAccount(uid, new Credentials(email, username, password), scanner) instanceof Customer){
				User chosenUser = getUser((Customer) (getAccount(uid, new Credentials(email, username, password), scanner)), scanner);
				showMenu(chosenUser, scanner, (Customer) (getAccount(uid, new Credentials(email, username, password), scanner)));
			} else if (getAccount(uid, new Credentials(email, username, password), scanner) instanceof Admin){
				showMenu((Admin) (getAccount(uid, new Credentials(email, username, password), scanner)), scanner);
			} else {
				showMenu((Employee)(getAccount(uid, new Credentials(email, username, password), scanner)), scanner);
			}
		} else {
			System.out.println("Incorrect password.");
			getPassword(scanner, username);
		}
	}
	
	private static void showMenu(Employee employee, Scanner scanner) {
		System.out.println("Welcome, " + employee.getEmployeeName() + "!");
		System.out.println("Please choose an option:");
		System.out.println("1) Lookup customer account");
		System.out.println("2) Log out");
		switch(scanner.nextLine()) {
		case "1":
			customerLookup(scanner, employee);
			break;
		case "2":
			System.out.println("Okay, bye!");
			startUI(scanner);
			break;
		}
	}
	
	private static void customerLookup(Scanner scanner, Employee employee) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(employee, scanner);
	}

	private static void showMenu(Admin admin, Scanner scanner) {
		System.out.println("Welcome, " + admin.getEmployeeName() + "!");
		System.out.println("Please choose an option:");
		System.out.println("1) Access/edit/delete customer account");
		System.out.println("2) Create employee account");
		System.out.println("3) Log out");
		switch(scanner.nextLine()) {
		case "1":
			adminAccess(scanner, admin);
			break;
		case "2":
			createEmployee(scanner,admin);
			break;
		case "3":
			System.out.println("Okay, bye!");
			startUI(scanner);
			break;
		}
	}
	
	private static void createEmployee(Scanner scanner, Admin admin) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(admin, scanner);
	}

	private static void adminAccess(Scanner scanner, Admin admin) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(admin, scanner);
	}

	private static Customer makeAccount(Credentials credentials, Scanner scanner) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("INSERT INTO accounts VALUES(DEFAULT, FALSE, FALSE)");
			statement.executeUpdate();
			statement = connection.prepareStatement("INSERT INTO credentials VALUES(DEFAULT, ?, ?, ?)");
			statement.setString(1, credentials.getEmail());
			statement.setString(2, credentials.getUsername());
			statement.setString(3, credentials.getPassword());
			statement.executeUpdate();
			statement = connection.prepareStatement("SELECT account_uid FROM credentials WHERE account_username = ?");
			statement.setString(1, credentials.getUsername());
			results = statement.executeQuery();
			while(results.next()) {
				return new Customer(results.getInt("account_uid"), credentials);
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
	
	private static void makeBalances(Customer customer) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("INSERT INTO balances VALUES(?, ?, ?)");
			statement.setInt(1, customer.getUid());
			statement.setBigDecimal(2, new BigDecimal(0));
			statement.setInt(3, 0);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	private static String getEmployeeName(int uid) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT employee_name FROM employeeInfo WHERE account_uid = ?");
			statement.setInt(1, uid);
			results = statement.executeQuery();
			while (results.next()) {
				return results.getString("employee_name");
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
	
	private static Account getAccount(int uid, Credentials credentials, Scanner scanner) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT account_isEmployee, account_isAdmin FROM accounts WHERE account_uid = ?");
			statement.setInt(1, uid);
			results = statement.executeQuery();
			while(results.next()) {
				if(results.getBoolean("account_isEmployee") && !results.getBoolean("account_isAdmin")) {
					return new Employee(uid, credentials, getEmployeeName(uid));
				} else if (results.getBoolean("account_isAdmin")) {
					return new Admin(uid, credentials, getEmployeeName(uid));
				} else {
					return new Customer(uid, credentials);
				}
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
	
	private static void showMenu(User user, Scanner scanner, Customer customer) {
		System.out.println("Welcome, " + user.getNickname() + "! You have $" + customer.getWalletBalance() + " in your wallet and " + customer.getTicketBalance() + " tickets to redeem.");
		System.out.println("Please choose an option:");
		System.out.println("1) Account details");
		System.out.println("2) Play games");
		System.out.println("3) Redemption store");
		System.out.println("4) Switch user");
		System.out.println("5) Log out");
		switch(scanner.nextLine()) {
		case "1":
			viewDetails(user, scanner, customer);
			break;
		case "2":
			listGames(scanner, user, customer);
			break;
		case "3":
			viewStore(scanner, user, customer);
			break;
		case "4":
			User chosenUser = getUser(customer, scanner);
			showMenu(chosenUser, scanner, customer);
			break;
		case "5":
			System.out.println("Okay, bye!");
			startUI(scanner);
			break;
		}
	}
	
	private static void viewStore(Scanner scanner, User user, Customer customer) {
		System.out.println("Please select an item to redeem your " + customer.getTicketBalance() + " tickets for.");
		List<Prize> prizes = Prizes.getPrizes();
		for(Prize prize : prizes) {
			System.out.println(prize.getUid() + ") " + prize.getName() + " - " + prize.getCost() + " tickets");
		}
		System.out.println("11) Go back");
		String userSelection = scanner.nextLine();
		if (userSelection.equals("11")){
			showMenu(user, scanner, customer);
		}
		for(Prize prize : prizes) {
			if(Integer.parseInt(userSelection) == prize.getUid()) {
				if(customer.getTicketBalance() < prize.getCost()) {
					System.out.println("You don't have enough tickets for this item.");
					viewStore(scanner, user, customer);
				} else {
					if(customer.getAddress() == null) {
						System.out.println("You haven't set your address yet, so we can't mail this to you.");
						showMenu(user, scanner, customer);
					} else {
						System.out.println(prize.confirm(customer));
					}
				}
				if(scanner.nextLine().trim().toUpperCase().equals("Y")) {
					prize.deductTickets(customer);
					System.out.println("Your order has been placed!");
					showMenu(user, scanner, customer);
				} else {
					viewStore(scanner, user, customer);
				}
			}
		}
	}
	
	private static void viewDetails(User user, Scanner scanner, Customer customer) {
			System.out.println("Email: " + customer.getCredentials().getEmail());
			System.out.println("Username: " + customer.getCredentials().getUsername());
			if(customer.getAddress() == null) {
				System.out.println("Address: not set");
			} else {
				System.out.println("Address: " + customer.getAddress().formatAddress());
			}
			String ownerString = new String();
			for(User customerUser : customer.getUsers()) {
				if (customerUser.isOwner()) {
					ownerString = customerUser.getNickname();
				}
			}
			System.out.println("Account owner: " + ownerString);
			if(!user.isOwner()) {
				System.out.println("1) Go back");
				if(scanner.nextLine().equals("1")) {
					showMenu(user, scanner, customer);
				}
			} else if (user.isOwner()) {
				System.out.println("1) Change email");
				System.out.println("2) Change username");
				System.out.println("3) Change address");
				System.out.println("4) Change owner");
				System.out.println("5) Add user");
				System.out.println("6) Add wallet funds");
				System.out.println("7) Transfer funds to another Arcade account");
				System.out.println("8) Go back");
				switch(scanner.nextLine()) {
				case "1":
					updateEmail(user, scanner, customer);
					break;
				case "2":
					updateUsername(user, scanner, customer);
					break;
				case "3":
					updateAddress(user, scanner, customer);
					break;
				case "4":
					updateOwner(user, scanner, customer);
					break;
				case "5":
					addUser(user, scanner, customer);
					break;
				case "6":
					updateWallet(user, scanner, customer);
					break;
				case "7":
					transferFunds(user, scanner, customer);
					break;
				case "8":
					showMenu(user, scanner, customer);
					break;
				}
			}
	}
	
	private static void addUser(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	private static void transferFunds(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	private static void updateOwner(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	private static void updateAddress(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	private static void updateUsername(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	private static void updateEmail(User user, Scanner scanner, Customer customer) {
		System.out.println("This method is not yet implemented. Returning to menu.");
		showMenu(user, scanner, customer);
	}

	public static void updateWallet(User user, Scanner scanner, Customer customer) {
		System.out.println("Your wallet currently has $" + customer.getWalletBalance() + ". How much would you like to add?");
		BigDecimal addedAmount = new BigDecimal(scanner.nextLine());
		customer.setWalletBalance(addedAmount);
		System.out.println("Transaction complete. Your wallet now has $" + customer.getWalletBalance());
		showMenu(user, scanner, customer);
	}
	
	private static User getUser(Customer customer, Scanner scanner) {
			System.out.println("What user are you?");
			List<User> customerUsers = customer.getUsers();
			int counter = 1;
			for(User user : customerUsers) {
				user.setChooseNum(counter);
				if(user.isOwner()) {
					System.out.println(user.getChooseNum() + ") " + user.getNickname() + " (account owner)");
				} else {
					System.out.println(user.getChooseNum() + ") " + user.getNickname());
				}
				counter++;
			}
			
			String userChoice = scanner.nextLine();
			
			for(User user : customerUsers) {
				if(Integer.parseInt(userChoice) == user.getChooseNum()) {
					return user;
				} 
			}
			return null;
			
	}
	
	private static void signUp(Scanner scanner) {
		System.out.println("Please enter your email:");
		String inputEmail = scanner.nextLine();
		createUsername(scanner, inputEmail);
	}
	
	private static void createUsername(Scanner scanner, String email) {
		System.out.println("Thank you. Please create a username:");
		String inputUsername = scanner.nextLine();
		List<String> usernames = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("SELECT account_username FROM credentials");
			results = statement.executeQuery();
			while(results.next()) {
				usernames.add(results.getString("account_username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeResultSet(results);
			ConnectionUtil.closeConnection(connection);
		}
		if(usernames.contains(inputUsername)) {
			System.out.println("Username already exists. Try again.");
			signUp(scanner);
		} else {
			System.out.println("Username available. Please create a password:");
			String password = scanner.nextLine();
			Credentials thisCredentials = new Credentials(email, inputUsername, password);
			Customer thisAccount = makeAccount(thisCredentials, scanner);
			makeBalances(thisAccount);
			System.out.println("Account created. What should we call your user?");
			String nickname = scanner.nextLine();
			User thisUser = createUser(thisAccount, nickname, true);
			showMenu(thisUser, scanner, thisAccount);
		}
	}

	private static User createUser(Customer customer, String nickname, boolean isOwner) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?, ?)");
			statement.setInt(1, customer.getUid());
			statement.setString(2, nickname);
			statement.setBoolean(3, isOwner);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
		return new User(nickname, isOwner);
	}
	
	private static void listGames(Scanner scanner, User user, Customer customer) {
		System.out.println("Please select a game to play.");
		List<Game> games = Games.getGames();
		for(Game game : games) {
			System.out.println(game.getUid() + ") " + game.getName() + " - $" + game.getPrice());
		}
		String userSelection = scanner.nextLine();
		for(Game game : games) {
			if(Integer.parseInt(userSelection) == game.getUid()) {
				System.out.println(game.confirm());
				if(scanner.nextLine().trim().toUpperCase().equals("Y")) {
					game.play(customer);
					game.getResults(customer);
					showMenu(user, scanner, customer);
				} else {
					listGames(scanner, user, customer);
				}
			}

		}
	}
	
	public static void startUI(Scanner scanner) {
		
		System.out.println("Welcome to the Online Arcade! Please select an option.");
		System.out.println("1) Log in \n2) Sign up \n3) About");
		
		switch (scanner.nextLine()) {
		case "1":
			getUsername(scanner);
			break;
		case "2":
			signUp(scanner);
			break;
		case "3":
			printAbout(scanner);
			break;
		default:
			System.out.println("Invalid input. Restarting.");
			startUI(scanner);
			break;
		}
	}
}
