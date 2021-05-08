package com.maat.mongo;


import com.maat.servicecommons.serverconfig.ServerConfig;
import com.mongodb.client.MongoClient;

public interface MongoClientFactory {
    MongoClient getMongoClient(ServerConfig serverConfig);
}
