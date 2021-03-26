package com.maat.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MongoConfiguration {
//
//    @Bean
//    public MongoTemplate mongoTemplate(MongoTemplateFactory mongoTemplateFactory){
//        return mongoTemplateFactory.getGlobalMongoForModule("READER_DEFAULT");
//    }

    @Bean
    public ExecutorService configExecutor() {
        return Executors.newCachedThreadPool();
    }
}
