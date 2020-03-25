package dataObjects;

import java.util.List;

public class GameCharacter {
	public String _id;
	public String name;
	public Weapon weapon;
	public Armor armor;
	public List<Item> inventory;
	
public GameCharacter() {
		
	}
	
	public GameCharacter(String name, Weapon weapon, Armor armor, String _id, List<Item> inventory) {
		this._id = _id;
		this.name = name;
		this.weapon = weapon;
		this.armor = armor;
		this.inventory = inventory;
	}
}
