package mongoDB;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.*;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dataObjects.GameCharacter;

public class MongoDB_Reader {
	
	private static final String CHARACTER_DATABASE_NAME = "characters";
	private static final String CHARACTER_COLLECTION_NAME = "characters";
	MongoClient mongoDatabaseConnectionPool;
	
	public MongoDB_Reader(MongoClient mongoDatabaseConnectionPool) {
		this.mongoDatabaseConnectionPool = mongoDatabaseConnectionPool;
	}
	
	public GameCharacter readCharacterFromMongoDB_byIndex(int characterIndex) {
		MongoCollection<GameCharacter> characterCollection = getCharacterCollection();
		FindIterable<GameCharacter> characterIterables = characterCollection.find();
		List<GameCharacter> characterList = new ArrayList<GameCharacter>();
		for(GameCharacter character : characterIterables) {
			characterList.add(character);
		}
		return characterList.get(characterIndex);
	}
	
	public GameCharacter readCharacterFromMongoDB_byID(String _id) {
		MongoCollection<GameCharacter> characterCollection = getCharacterCollection();
		FindIterable<GameCharacter> characterIterables = characterCollection.find();
		return findByID(characterIterables, _id);
	}

	private GameCharacter findByID(FindIterable<GameCharacter> characterIterables, String _id) {
		for(GameCharacter gameCharacter : characterIterables ) {
			if(gameCharacter._id.equals(_id)) {
				return gameCharacter;
			}
		}
		return null;
	}

	private MongoCollection<GameCharacter> getCharacterCollection() {
		MongoDatabase characterDatabase = mongoDatabaseConnectionPool.getDatabase(CHARACTER_DATABASE_NAME);
		CodecRegistry pojoCodecRegistry = buildPOJO_codecRegistry();

		characterDatabase = characterDatabase.withCodecRegistry(pojoCodecRegistry);
		
		MongoCollection<GameCharacter> characterCollection = characterDatabase.getCollection(CHARACTER_COLLECTION_NAME, GameCharacter.class);
		return characterCollection;
	}

	private CodecRegistry buildPOJO_codecRegistry() {
		CodecRegistry defaultCodecRegistry = MongoClient.getDefaultCodecRegistry();
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
		return pojoCodecRegistry;
	}
}
