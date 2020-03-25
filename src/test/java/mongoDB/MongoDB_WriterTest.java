package mongoDB;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import dataObjects.*;
import exceptions.DatabaseNotConnectedException;
import mongoDB.MongoDB_Writer;

class MongoDB_WriterTest {
	
	static MongoDB_Writer mongoDB_Writer;

	@BeforeAll
	static void testPreparation() throws DatabaseNotConnectedException {
		mongoDB_Writer = new MongoDB_Writer();
		mongoDB_Writer.dropCharactersCollection();
	}
	
	@Test
	void establishConnectionToMongoDB_shouldNotThrowAnDatabaseNotConnectedException() {
		
		
		try {
			mongoDB_Writer.establishConnectionToMongoDB();
			
		} catch (DatabaseNotConnectedException e) {
			fail(e);
		}
	}
	
	@Test
	void writeCharacterToMongoDB_shouldNotThrowAnException() {
		GameCharacter sylvia = GameCharacters.sylvia();
		
		try {
			mongoDB_Writer.writeCharacterToMongoDB(sylvia);
		} catch (Exception e) {
			fail(e);
		}
	}

}
