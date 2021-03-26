package com.maat.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoTemplateFactory {
    MongoTemplate getGlobalMongoForModule(String moduleName);
}
