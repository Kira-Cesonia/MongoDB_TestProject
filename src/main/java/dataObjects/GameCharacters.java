package dataObjects;

import java.util.ArrayList;
import java.util.List;

public class GameCharacters {
	
	public static GameCharacter sylvia() {
		
		String characterName = "Sylvia Zerin";
		
		String weaponName = "Gryphclaw";
		int attack = 255;
		Weapon weapon = new Weapon(weaponName, attack);
		
		String armorName = "Starscale Armor";
		int defense = 231;
		Armor armor = new Armor(armorName, defense);
		
		String _id = "sylv01";
		
		List<Item> inventory = new ArrayList<Item>();
		Potion potion = new Potion("Hi-Potion", 3);
		inventory.add(potion);
		Ether ether = new Ether("Mega Ether", 5);
		inventory.add(ether);
		
		GameCharacter sylvia = new dataObjects.GameCharacter(characterName, weapon, armor, _id, inventory);
		return sylvia;
	}
}
