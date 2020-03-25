package mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

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
}
