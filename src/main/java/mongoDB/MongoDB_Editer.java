package mongoDB;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dataObjects.GameCharacter;

public class MongoDB_Editer {
	
	MongoClient mongoDatabaseConnectionPool;
	private static final String CHARACTER_DATABASE_NAME = "characters";
	private static final String CHARACTER_COLLECTION_NAME = "characters";
	
	public MongoDB_Editer(MongoClient mongoDatabaseConnectionPool) {
		this.mongoDatabaseConnectionPool = mongoDatabaseConnectionPool;
	}
	
	public void updateCharacter(GameCharacter character) {
		MongoCollection<GameCharacter> characterCollection = getCharacterCollection();
		Bson filter = eq("_id", character._id);
		characterCollection.replaceOne(filter, character);
	}
	
	private MongoCollection<GameCharacter> getCharacterCollection() {
		MongoDatabase characterDatabase = mongoDatabaseConnectionPool.getDatabase(CHARACTER_DATABASE_NAME);
		MongoDB_Operations mongoDB_operations = new MongoDB_Operations(mongoDatabaseConnectionPool);
		CodecRegistry pojoCodecRegistry = mongoDB_operations.buildPOJO_codecRegistry();

		characterDatabase = characterDatabase.withCodecRegistry(pojoCodecRegistry);
		
		MongoCollection<GameCharacter> characterCollection = characterDatabase.getCollection(CHARACTER_COLLECTION_NAME, GameCharacter.class);
		return characterCollection;
	}
}
