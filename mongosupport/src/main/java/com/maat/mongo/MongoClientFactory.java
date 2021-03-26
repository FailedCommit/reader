package com.maat.mongo;


import com.maat.mongo.serverconfig.beans.ServerConfig;
import com.mongodb.client.MongoClient;

public interface MongoClientFactory {
    MongoClient getMongoClient(ServerConfig serverConfig);
}
