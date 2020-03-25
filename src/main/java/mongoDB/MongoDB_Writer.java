package mongoDB;


import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dataObjects.GameCharacter;
import exceptions.DatabaseNotConnectedException;

public class MongoDB_Writer {
	
	private static final String CHARACTER_DATABASE_NAME = "characters";
	private static final String CHARACTER_COLLECTION_NAME = "characters";
	
	MongoCollection<GameCharacter> characterCollection;
	
	public MongoDB_Writer() throws DatabaseNotConnectedException {
		MongoDB_Connection mongoDB_connection = establishConnectionToMongoDB();
		MongoClient mongoDatabaseConnectionPool = mongoDB_connection.mongoDatabaseConnectionPool;
		
		MongoDB_Operations mongoDB_operations = new MongoDB_Operations(mongoDatabaseConnectionPool);
		CodecRegistry pojoCodecRegistry = mongoDB_operations.buildPOJO_codecRegistry();
		MongoDatabase characterDatabase = mongoDatabaseConnectionPool.getDatabase(CHARACTER_DATABASE_NAME);
		characterDatabase = characterDatabase.withCodecRegistry(pojoCodecRegistry);
	
		characterCollection = characterDatabase.getCollection(CHARACTER_COLLECTION_NAME, GameCharacter.class);
	}

	public void writeCharacterToMongoDB(GameCharacter character) throws DatabaseNotConnectedException {
		characterCollection.insertOne(character);
	}
	
	public void dropCharactersCollection() {
		characterCollection.drop();
	}
	
	public MongoDB_Connection establishConnectionToMongoDB() throws DatabaseNotConnectedException {
		MongoDB_Connection mongoDB_connection = new MongoDB_Connection();
		return mongoDB_connection;
	}
}
