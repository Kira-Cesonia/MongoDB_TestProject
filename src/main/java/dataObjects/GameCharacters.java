package dataObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		
		Element element = new Element("Fauna");
		
		GameCharacter sylvia = new GameCharacter(
			characterName,
			weapon,
			armor,
			_id,
			inventory,
			element 
		);
		return sylvia;
	}
	
	public static GameCharacter sylviaEdited() {
		
		String characterName = "Sylvia the Hero";
		
		String weaponName = "Gryphclaw+";
		int attack = 512;
		Weapon weapon = new Weapon(weaponName, attack);
		
		String armorName = "Starscale Armor EX";
		int defense = 432;
		Armor armor = new Armor(armorName, defense);
		
		String _id = "sylv01";
		
		List<Item> inventory = new ArrayList<Item>();
		Potion potion = new Potion("Hi-Potion", 99);
		inventory.add(potion);
		Ether ether = new Ether("Mega Ether", 78);
		inventory.add(ether);
		
		Element element = new Element("Minayero");
		
		GameCharacter sylvia = new GameCharacter(
			characterName,
			weapon,
			armor,
			_id,
			inventory,
			element
		);
		return sylvia;
	}
	
	public static GameCharacter henry() {
		
		String characterName = "Henry Felkora";
		
		String weaponName = "Boomerang";
		int attack = 12;
		Weapon weapon = new Weapon(weaponName, attack);
		
		String armorName = "Tattered Clothes";
		int defense = 6;
		Armor armor = new Armor(armorName, defense);
		
		String _id = UUID.randomUUID().toString();
		
		List<Item> inventory = new ArrayList<Item>();
		Ether ether = new Ether("Ether", 4);
		inventory.add(ether);
		
		Element element = new Element("Light");
		
		GameCharacter henry = new GameCharacter(
			characterName,
			weapon,
			armor,
			_id,
			inventory,
			element
		);
		return henry;
	}
	
	public static GameCharacter dani() {
		
		String characterName = "Dani Felkora";
		
		String weaponName = "Gnarled Rod";
		int attack = 9;
		Weapon weapon = new Weapon(weaponName, attack);
		
		String armorName = "Robe";
		int defense = 9;
		Armor armor = new Armor(armorName, defense);
		
		String _id = UUID.randomUUID().toString();
		
		List<Item> inventory = new ArrayList<Item>();
		Potion potion = new Potion("Potion", 7);
		inventory.add(potion);
		
		Element element = new Element("Darkness");
		
		GameCharacter dani = new GameCharacter(
			characterName,
			weapon,
			armor,
			_id,
			inventory,
			element
		);
		return dani;
	}
}
