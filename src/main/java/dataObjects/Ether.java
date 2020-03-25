package dataObjects;

public class Ether extends Item {
	
	public static final String DISCRIMINATOR = "dataObjects.Ether";
	
	public Ether() {
	}
	
	public Ether(String name, int amount) {
		super(name, amount);
		_t = DISCRIMINATOR;
	}
}
