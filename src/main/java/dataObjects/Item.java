package dataObjects;

public abstract class Item {
	public String name;
	public int amount;
	public String _t;
	
	public Item() {
		
	}
	
	public Item(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
}
