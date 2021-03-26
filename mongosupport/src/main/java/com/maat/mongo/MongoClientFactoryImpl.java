package com.maat.mongo;

import com.maat.core.utils.MCollectionUtils.Transformer;
import com.maat.core.utils.MConcurrentUtils.Factory;
import com.maat.core.utils.MStringUtils;
import com.maat.mongo.config.DefaultMongoClientOptions;
import com.maat.mongo.serverconfig.beans.HostConfig;
import com.maat.mongo.serverconfig.beans.MongoClientOptions;
import com.maat.mongo.serverconfig.beans.ServerConfig;
import com.maat.mongo.serverconfig.beans.ServerHost;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.SslSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.maat.core.utils.MCollectionUtils.transformToList;
import static com.maat.core.utils.MConcurrentUtils.getOrCreateDefault;
import static com.maat.core.utils.MConcurrentUtils.sharedStrippedLocks;
import static com.mongodb.MongoCredential.createCredential;

@Component
@RequiredArgsConstructor
@Slf4j
public class MongoClientFactoryImpl implements MongoClientFactory {
    private static final int MONGO_DEFAULT_PORT = 27017;
    private final DefaultMongoClientOptions defaultMongoClientOptions;
    private final ConcurrentMap<String, MongoClient> mongoClientMap = new ConcurrentHashMap<>();
    private static final Factory<String, MongoClient, ServerConfig> MONGO_CLIENT_FACTORY =
            (key, config) -> createMongoClient(config);
    private static final Transformer<ServerHost, ServerAddress> SERVER_HOST_TO_ADDRESS_TRANSFORMER = serverHost -> {
        int port = serverHost.getPort();
        if(port == 0) {
            port = MONGO_DEFAULT_PORT;
        }
        return new ServerAddress(serverHost.getHost(), port);
    };

    @Override
    public MongoClient getMongoClient(ServerConfig serverConfig) {
        HostConfig hostConfig = serverConfig.getHostConfig();
        String key = createKey(hostConfig);
        final MongoClientOptions mongoClientOptions = transformClientOptions(
                defaultMongoClientOptions, serverConfig.getHostConfig().getMongoClientOptions());
        hostConfig.setMongoClientOptions(mongoClientOptions);
        return getOrCreateDefault(sharedStrippedLocks, mongoClientMap, key, MONGO_CLIENT_FACTORY, serverConfig);
    }

    private static MongoClient createMongoClient(ServerConfig serverConfig) {
        MongoClientSettings mongoClientSettings = createMongoSettings(serverConfig);
        return MongoClients.create(mongoClientSettings);
    }

    private static MongoClientSettings createMongoSettings(ServerConfig serverConfig) {
        final HostConfig hostConfig = serverConfig.getHostConfig();
        final String username = hostConfig.getUserName();
        final String dbName = serverConfig.getDbName();
        final char[] password = hostConfig.getPassword();

        val builder =MongoClientSettings.builder();
        if(MStringUtils.nonBlank(username)){
                builder.credential(createCredential(username, dbName, password));
                    }
              return   builder.applyToClusterSettings(cs -> cs.applySettings(getClusterSettings(serverConfig, hostConfig)))
                .applyToSslSettings(ss -> ss.applySettings(getSslSettings(hostConfig)))
                .build();
    }

    private static SslSettings getSslSettings(HostConfig hostConfig) {
        // TODO: checkout invalidHostNameAllowed and sslContext
        return SslSettings.builder().enabled(hostConfig.isSslEnabled()).build();
    }

    private static ClusterSettings getClusterSettings(ServerConfig serverConfig, HostConfig hostConfig) {
        List<ServerAddress> mongoServerAddresses = transformToList(
                serverConfig.getHostConfig().getHosts(), SERVER_HOST_TO_ADDRESS_TRANSFORMER);
        return ClusterSettings.builder().hosts(mongoServerAddresses).build();
    }

    private MongoClientOptions transformClientOptions(DefaultMongoClientOptions defaultMongoClientOptions, MongoClientOptions mongoClientOptions) {
        //TODO: Review and implement mongoClient options if required
        return null;
    }

    private String createKey(HostConfig hostConfig) {
        return hostConfig.getClusterName() + "-" + hostConfig.getId();
    }
}
