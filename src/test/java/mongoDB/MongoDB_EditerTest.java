package mongoDB;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mongodb.MongoClient;

import dataObjects.GameCharacter;
import dataObjects.GameCharacters;
import exceptions.DatabaseNotConnectedException;

class MongoDB_EditerTest {
	
	private static final int TEST_CHARACTER_INDEX = 0;
	private static final String TEST_CHARACTER_ID = "sylv01";
	private static final String EXPECTED_CHARACTER_NAME = "Sylvia Zerin";
	private static final String EXPECTED_WEAPON_NAME = "Luberjack's Axe";
	private static final int EXPECTED_POTION_AMOUNT = 2;
	private static final int EXPECTED_ARMOR_DEFENSE = 13;
	private static MongoDB_Reader mongoDB_Reader;
	private static MongoDB_Editer mongoDB_Editer;
	private static MongoDB_Writer mongoDB_Writer;

	@BeforeAll
	static void setUp() throws DatabaseNotConnectedException {
		MongoDB_Connection mongoDB_connection = new MongoDB_Connection();
		MongoClient mongoDatabaseConnectionPool = mongoDB_connection.mongoDatabaseConnectionPool;
		mongoDB_Reader = new MongoDB_Reader(mongoDatabaseConnectionPool);
		mongoDB_Writer = new MongoDB_Writer();
		mongoDB_Editer = new MongoDB_Editer(mongoDatabaseConnectionPool);
		mongoDB_Writer.dropCharactersCollection();
		GameCharacter sylvia = GameCharacters.sylvia();
		mongoDB_Writer.writeCharacterToMongoDB(sylvia);
		mongoDB_Editer.updateCharacter(GameCharacters.sylviaEdited());
	}
	
	@Test
	void readCharacterFromMongoDB_byIndex_shouldReturnCharacterWithCorrectName() {
		GameCharacter testCharacter = mongoDB_Reader.readCharacterFromMongoDB_byIndex(TEST_CHARACTER_INDEX);
		
		assertEquals(EXPECTED_CHARACTER_NAME, testCharacter.name);
	}
	
	@Test
	void readCharacterFromMongoDB_byIndex_shouldReturnCharacterWithCorrectWeaponName() {
		GameCharacter testCharacter = mongoDB_Reader.readCharacterFromMongoDB_byIndex(TEST_CHARACTER_INDEX);
		
		String actualWeaponName = testCharacter.weapon.name;
		
		assertEquals(EXPECTED_WEAPON_NAME, actualWeaponName);
	}
	
	@Test
	void readCharacterFromMongoDB_byIndex_shouldReturnCharacterWithCorrectArmorDefense() {
		GameCharacter testCharacter = mongoDB_Reader.readCharacterFromMongoDB_byIndex(TEST_CHARACTER_INDEX);
		
		int actualArmorDefense = testCharacter.armor.defense;
		
		assertEquals(EXPECTED_ARMOR_DEFENSE, actualArmorDefense);
	}
	
	@Test
	void readCharacterFromMongoDB_byID_shouldReturnCharacterWithCorrectName() {
		GameCharacter testCharacter = mongoDB_Reader.readCharacterFromMongoDB_byID(TEST_CHARACTER_ID);
		
		assertEquals(EXPECTED_CHARACTER_NAME, testCharacter.name);
	}
	
	@Test
	void readCharacterFromMongoDB_byID_shouldReturnCharacterWithCorrectPotionAmount() {
		GameCharacter testCharacter = mongoDB_Reader.readCharacterFromMongoDB_byID(TEST_CHARACTER_ID);

		int expectedPotionAmount = testCharacter.inventory.get(0).amount;
		
		assertEquals(EXPECTED_POTION_AMOUNT, expectedPotionAmount);
	}

}
