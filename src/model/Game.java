package model;

import java.math.BigDecimal;

public class Game {
	
	private String name;
	private BigDecimal price;
	
	public Game(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}
	
	public String play() {
		return ("Playing " + this.name + "...");
	}
	
	public int getTickets() {
		return (int)(Math.random() * 10); //generate between 0 and 10 tickets
	}
	
	public String getResults() {
		if(this.getTickets() == 0) {
			return "You did horribly. You didn't get a single ticket.";
		} else if (this.getTickets() >= 1 && this.getTickets() <= 3) {
			return "You did poorly. You only got " + this.getTickets() + " tickets.";
		} else if (this.getTickets() >= 4 && this.getTickets() <=6) {
			return "You did an average job. You got " + this.getTickets() + " tickets.";
		} else if (this.getTickets() >= 7 && this.getTickets() <= 9) {
			return "You did a good job. You got " + this.getTickets() + " tickets.";
		} else if (this.getTickets() == 10) {
			return "You did amazing! You got ten tickets.";
		}
		return null;
	}
	
}
