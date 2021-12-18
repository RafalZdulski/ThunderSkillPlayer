package com.thunderfinesse.thunderskillfetcher.player.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class AccessPoint {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private String clientURI;

    public AccessPoint(String clientURI, String dbName){
        this.clientURI = clientURI;
        setDatabase(dbName);
    }

    public MongoDatabase setDatabase(String dbName) {
        mongoClient = MongoClients.create(clientURI);
        return database = mongoClient.getDatabase(dbName);
    }

    public MongoCollection<Document> getCollection(String collection){
        if(database.getCollection(collection) == null)
            database.createCollection(collection);
        return database.getCollection(collection);
    }

    public String getDatabaseName() {
        return database.getName();
    }
}
