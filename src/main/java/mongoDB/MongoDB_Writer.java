package mongoDB;


import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
		
		CodecRegistry pojoCodecRegistry = buildPOJO_codecRegistry();
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

	private CodecRegistry buildPOJO_codecRegistry() {
		CodecRegistry defaultCodecRegistry = MongoClient.getDefaultCodecRegistry();
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
		return pojoCodecRegistry;
	}
}
