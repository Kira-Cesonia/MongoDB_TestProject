package dataObjects;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator
public abstract class Item {
	public String name;
	public int amount;
	
	public Item() {
		
	}
	
	public Item(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
}
