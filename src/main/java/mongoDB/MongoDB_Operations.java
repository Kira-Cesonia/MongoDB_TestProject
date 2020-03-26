package mongoDB;

import static org.bson.codecs.configuration.CodecRegistries.*;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

import codecs.GameCharacterCodec;
import codecs.ItemCodec;
import dataObjects.Armor;
import dataObjects.Ether;
import dataObjects.GameCharacter;
import dataObjects.Item;
import dataObjects.Potion;
import dataObjects.Weapon;

public class MongoDB_Operations {
    MongoClient mongoDatabaseConnectionPool;

    public MongoDB_Operations(MongoClient mongoDatabaseConnectionPool){
        this.mongoDatabaseConnectionPool = mongoDatabaseConnectionPool;
    }

    public int returnDatabaseCount() {
        MongoIterable<String> databaseNames = mongoDatabaseConnectionPool.listDatabaseNames();
        int databaseCount = 0;
        for(@SuppressWarnings("unused") String ignored : databaseNames){
            databaseCount++;
        }
        return databaseCount;
    }

    public boolean databaseExists(String expectedDatabaseName) {
        MongoIterable<String> databaseNames = mongoDatabaseConnectionPool.listDatabaseNames();
        for (String databaseName : databaseNames) {
            if (databaseName.equals(expectedDatabaseName)) {
                return true;
            }
        }
        return false;
    }
    
    public CodecRegistry buildPOJO_codecRegistry() {
		CodecRegistry defaultCodecRegistry = MongoClient.getDefaultCodecRegistry();
		
		//ClassModelBuilder<GameCharacter> gameCharacterModelBuilder = ClassModel.builder(GameCharacter.class);
		//gameCharacterModelBuilder = gameCharacterModelBuilder.enableDiscriminator(true);
		//ClassModel<GameCharacter> gameCharacterModel = gameCharacterModelBuilder.build();
		
		ClassModel<Weapon> weaponModel = ClassModel.builder(Weapon.class).enableDiscriminator(true).build();
		ClassModel<Armor> armorModel = ClassModel.builder(Armor.class).enableDiscriminator(true).build();
		//ClassModel<Item> itemModel = ClassModel.builder(Item.class).enableDiscriminator(true).build();
		ClassModel<Potion> potionModel = ClassModel.builder(Potion.class).enableDiscriminator(true).build();
		ClassModel<Ether> etherModel = ClassModel.builder(Ether.class).enableDiscriminator(true).build();

		
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider
			.builder()
			.register(
				//gameCharacterModel,
				weaponModel,
				armorModel,
				//itemModel,
				potionModel,
				etherModel
			)
			.build();
		
		CodecRegistry modelPojoCodecRegistry = fromRegistries(
			defaultCodecRegistry,
			fromProviders(pojoCodecProvider)
		);
		
		ItemCodec itemCodec = new ItemCodec(defaultCodecRegistry);
		GameCharacterCodec gameCharacterCodec = new GameCharacterCodec(modelPojoCodecRegistry, itemCodec);
		
		CodecRegistry pojoCodecRegistry = fromRegistries(
				modelPojoCodecRegistry,
				fromCodecs(
					itemCodec,
					gameCharacterCodec
				)
			);
		
		
		return pojoCodecRegistry;
	}
}
