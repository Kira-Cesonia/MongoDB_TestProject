package dataObjects;

public class Potion extends Item {
	
	public static final String DISCRIMINATOR = "dataObjects.Potion";

	public Potion() {
	}
	
	public Potion(String name, int amount) {
		super(name, amount);
		_t = DISCRIMINATOR;
	}
}
