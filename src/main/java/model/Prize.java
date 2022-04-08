package model;

public class Prize {
	private String name;
	private int cost;
	
	public Prize(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public String confirm() {
		return ("Redeem " + cost + " tickets for " + name + "? Y/N");
	}
	
}
