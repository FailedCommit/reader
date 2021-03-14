package com.maat.mongo;

import com.maat.mongo.serverconfig.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
public class MongoTemplateFactoryImpl implements MongoTemplateFactory {
    private final MongoClientFactory clientFactory;
    private final ServerConfigService serverConfigService;

    @Override
    public MongoTemplate getGlobalMongoForModule(String moduleName) {
        return null;
    }
}
