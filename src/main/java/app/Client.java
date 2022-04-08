package app;

import java.util.Scanner;

import repository.Games;

public class Client {
	
	private static void printAbout() {
		System.out.println("The Online Arcade works just like a regular arcade or carnival.");
		System.out.println("You can add some money to your digital wallet, and pay a little to play a game.");
		System.out.println("If you do really well at the game, you'll earn a lot of tickets.");
		System.out.println("Tickets can be redeemed for real gifts that we'll mail to you!");
	}
	
	private static void signIn(Scanner scanner) {
		System.out.println("Please enter your username.");
		listGames(scanner);
	}
	
	private static void signUp(Scanner scanner) {
		System.out.println("Please create a username:");
	}
	
	private static void listGames(Scanner scanner) {
		System.out.println("Please select a game.");
		String userSelection = scanner.nextLine();
		for(int i = 0; i<Games.games.length; i++) {
			System.out.println((i+1) + ") " + Games.games[i].getName());
			if (Integer.parseInt(userSelection) == i) {
			}
		}
		
	}
	
	public static void startUI(Scanner scanner) {
		
		System.out.println("Welcome to the Online Arcade! Please select an option.");
		System.out.println("1) Log in \n2) Sign up \n3) About");
		
		switch (scanner.nextLine()) {
		case "1":
			signIn(scanner);
			break;
		case "2":
			signUp(scanner);
			break;
		case "3":
			printAbout();
			break;
		default:
			System.out.println("Invalid input. Closing.");
			scanner.close();
			break;
		}
	}
}
