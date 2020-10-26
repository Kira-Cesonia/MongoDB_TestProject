package mongoDB;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import dataObjects.Element;
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
		List<GameCharacter> characterList = castToList(characterIterables);
		return characterList.get(characterIndex);
	}

	private List<GameCharacter> castToList(
		FindIterable<GameCharacter> characterIterables
	) {
		List<GameCharacter> characterList = new ArrayList<GameCharacter>();
		for (GameCharacter character : characterIterables) {
			characterList.add(character);
		}
		return characterList;
	}
	
	public GameCharacter readCharacterFromMongoDB_byID(String _id) {
		MongoCollection<GameCharacter> characterCollection = getCharacterCollection();
		FindIterable<GameCharacter> characterIterables = characterCollection.find();
		return findByID(characterIterables, _id);
	}
	
	private GameCharacter findByID(
		FindIterable<GameCharacter> characterIterables,
		String _id
	) {
		for (GameCharacter gameCharacter : characterIterables) {
			if (gameCharacter._id.equals(_id)) {
				return gameCharacter;
			}
		}
		return null;
	}
	
	public List<GameCharacter> readGameCharactersByElement(Element element) {
		MongoCollection<GameCharacter> characterCollection = getCharacterCollection();
		FindIterable<GameCharacter> gameCharacterIterables
			= characterCollection.find(Filters.eq("element", element));
		List<GameCharacter> gameCharacterList = castToList(gameCharacterIterables);
		return gameCharacterList;
	}
	
	private MongoCollection<GameCharacter> getCharacterCollection() {
		MongoDatabase characterDatabase
			= mongoDatabaseConnectionPool.getDatabase(CHARACTER_DATABASE_NAME);
		MongoDB_Operations mongoDB_operations
			= new MongoDB_Operations(mongoDatabaseConnectionPool);
		CodecRegistry pojoCodecRegistry = mongoDB_operations.buildPOJO_codecRegistry();
		
		characterDatabase = characterDatabase.withCodecRegistry(pojoCodecRegistry);
		
		MongoCollection<GameCharacter> characterCollection = characterDatabase
			.getCollection(CHARACTER_COLLECTION_NAME, GameCharacter.class);
		return characterCollection;
	}
	
}
