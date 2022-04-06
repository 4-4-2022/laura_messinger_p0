package main;

import java.util.Scanner;

import repository.Games;

public class Client {
	
	private static void signIn(Scanner scanner) {
		System.out.println("Please enter your username.");
	}
	
	private static void signUp(Scanner scanner) {
		System.out.println("Please create a username:");
	}
	
	private static void listGames(Scanner scanner) {
		System.out.println("Please select a game.");
		
		for(int i = 0; i<Games.games.length; i++) {
			System.out.println((i+1) + ") " + Games.games[i].getName());
		}
		
	}
	
	public static void startUI(Scanner scanner) {
		
		System.out.println("Welcome to the Online Arcade! Please select an option.");
		System.out.println("1) Sign in \n2) Sign up");
		
		switch (scanner.nextLine()) {
		case "1":
			signIn(scanner);
			break;
		case "2":
			signUp(scanner);
			break;
		default:
			System.out.println("Invalid input. Closing.");
			scanner.close();
			break;
		}
	}
}
