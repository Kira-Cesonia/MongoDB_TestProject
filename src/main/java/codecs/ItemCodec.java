package codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import dataObjects.Ether;
import dataObjects.Item;
import dataObjects.Potion;

public class ItemCodec implements Codec<Item> {
	
	static final String DISCRIMINATOR_FIELD_NAME = "_t";
	static final String NAME_FIELD_NAME = "nameAnly";
	static final String AMOUNT_FIELD_NAME = "amountAnly";
	
	private String discriminator;
		
	private CodecRegistry codecRegistry;
	
	public ItemCodec(CodecRegistry codecRegistry) {
	    this.codecRegistry = codecRegistry;
	}
	

	@Override
	public void encode(BsonWriter bsonWriter, Item itemData, EncoderContext encoderContext) {
		Document document = new Document();
		
		document.put(DISCRIMINATOR_FIELD_NAME,itemData._t);
		document.put(AMOUNT_FIELD_NAME,itemData.amount);
		document.put(NAME_FIELD_NAME,itemData.name);
		
		Codec<Document>documentCodec = new DocumentCodec(codecRegistry);
		documentCodec.encode(bsonWriter, document, encoderContext);
	}

	@Override
	public Item decode(BsonReader bsonReader, DecoderContext decoderContext) {
		Item item = null;
		
		bsonReader.readStartDocument();
		discriminator = bsonReader.readString(DISCRIMINATOR_FIELD_NAME);
		String name = bsonReader.readString(NAME_FIELD_NAME);
		int amount = bsonReader.readInt32(AMOUNT_FIELD_NAME);
		
		if(discriminator.equals(Potion.class.toString())) {
			item = new Potion(name, amount);
		}else if(discriminator.equals(Ether.class.toString())) {
			item = new Ether(name, amount);
		}
		bsonReader.readEndDocument();
		
		return item;
	}


	@Override
	public Class<Item> getEncoderClass() {
		return Item.class;
	}

}
