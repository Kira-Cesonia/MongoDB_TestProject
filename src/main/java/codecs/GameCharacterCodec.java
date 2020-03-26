package codecs;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import dataObjects.Armor;
import dataObjects.GameCharacter;
import dataObjects.Item;
import dataObjects.Weapon;

public class GameCharacterCodec implements Codec<GameCharacter>{
	
	private CodecRegistry codecRegistry;
	private ItemCodec itemCodec;
	
	private static final String ID_FIELD_NAME = "_id";
	private static final String NAME_FIELD_NAME = "name";
	private static final String WEAPON_FIELD_NAME = "weapon";
	private static final String ARMOR_FIELD_NAME = "armor";
	private static final String INVENTORY_FIELD_NAME = "inventory";

	
	public GameCharacterCodec(CodecRegistry codecRegistry, ItemCodec itemCodec) {
	    this.codecRegistry = codecRegistry;
	    this.itemCodec = itemCodec;
	}

	@Override
	public void encode(BsonWriter bsonWriter, GameCharacter gameCharacterData, EncoderContext encoderContext) {
		Document document = new Document();
		
		document.put(ID_FIELD_NAME, gameCharacterData._id);
		document.put(NAME_FIELD_NAME, gameCharacterData.name);
		document.put(WEAPON_FIELD_NAME, gameCharacterData.weapon);
		document.put(ARMOR_FIELD_NAME, gameCharacterData.armor);
		document.put(INVENTORY_FIELD_NAME, gameCharacterData.inventory);

		Codec<Document>documentCodec = new DocumentCodec(codecRegistry);
		documentCodec.encode(bsonWriter, document, encoderContext);
	}

	@Override
	public Class<GameCharacter> getEncoderClass() {
		return GameCharacter.class;
	}

	@Override
	public GameCharacter decode(BsonReader bsonReader, DecoderContext decoderContext) {
		Codec<Weapon> weaponCodec = codecRegistry.get(Weapon.class);
		Codec<Armor> armorCodec = codecRegistry.get(Armor.class);
		
		bsonReader.readStartDocument();
		String _id = bsonReader.readString(ID_FIELD_NAME);
		String name = bsonReader.readString(NAME_FIELD_NAME);
		Weapon weapon = weaponCodec.decode(bsonReader, decoderContext);
		Armor armor = armorCodec.decode(bsonReader, decoderContext);
		
		List<Item> inventory = new ArrayList<Item>();
		
		bsonReader.readStartArray();
		inventory.add(itemCodec.decode(bsonReader, decoderContext));
		bsonReader.readEndArray();
		
		bsonReader.readEndDocument();
		
		GameCharacter gameCharacter = new GameCharacter(name, weapon, armor, _id, inventory);
		
		return gameCharacter;
	}

}
