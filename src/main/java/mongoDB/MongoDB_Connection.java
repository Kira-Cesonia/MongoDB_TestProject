package mongoDB;

import com.mongodb.*;

import exceptions.DatabaseNotConnectedException;

import org.apache.commons.logging.*;

public class MongoDB_Connection {

    private static final Log LOGGER = LogFactory.getLog(MongoDB_Connection.class);
    private static final String DEFAULT_USERNAME = "cesonia";
    private static final String DEFAULT_PASSWORD = "pizzaismagic";
    private static final String DEFAULT_IP = "127.0.0.1"; // Docker Desktop: 127.0.0.1 / Docker Toolbox: 192.168.99.100
    private static final String DEFAULT_PORT = "27017";
    private static final String DEFAULT_DATABASE_NAME = "cesonia_database";
    private String username;
    private String password;
    private String ip;
    private String port;
    @SuppressWarnings("unused")
	private String databaseName;
    private MongoDB_Operations mongoDB_Operations;
    MongoClientURI mongoDBConnectionStringURI;
    MongoClient mongoDatabaseConnectionPool;

    public MongoDB_Connection() throws DatabaseNotConnectedException {
        this(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_IP, DEFAULT_PORT, DEFAULT_DATABASE_NAME);
    }

    public MongoDB_Connection(String username, String password, String ip, String port, String databaseName) throws DatabaseNotConnectedException {
        setConnectionParameters(username, password, ip, port);
        setDatabaseName(databaseName);
        MongoClient mongoDatabaseConnectionPool = connectToDatabase();
        MongoDB_Operations mongoDB_Operations = setMongoDBOperations(mongoDatabaseConnectionPool);
        checkIfConnectionExists(mongoDB_Operations);
    }

    private void setConnectionParameters(String username, String password, String ip, String port) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    private void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    private MongoClient connectToDatabase() {
        LOGGER.info("Trying to connect to MongoDB...");
        mongoDBConnectionStringURI = new MongoClientURI(buildDatabaseConnectionString());
        mongoDatabaseConnectionPool = new MongoClient(mongoDBConnectionStringURI);
        return mongoDatabaseConnectionPool;
    }

    private MongoDB_Operations setMongoDBOperations(MongoClient mongoDatabaseConnectionPool) {
        mongoDB_Operations = new MongoDB_Operations(mongoDatabaseConnectionPool);
        return mongoDB_Operations;
    }

    private String buildDatabaseConnectionString() {
        return "mongodb://" + username + ":" + password + "@" + ip + ":" + port + "/";
    }

    private void checkIfConnectionExists(MongoDB_Operations mongoDB_Operations) throws DatabaseNotConnectedException {
        try {
            mongoDB_Operations.returnDatabaseCount();
            LOGGER.info("Connection to Mongo DB successfully established.");
        } catch (MongoTimeoutException e) {
            String errorMessage = "Connection to MongoDB could not be established.";
            LOGGER.error(errorMessage);
            throw new DatabaseNotConnectedException(errorMessage);
        }
    }
}
