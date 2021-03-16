package com.maat.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMongoRepository<T, ID> extends SimpleMongoRepository<T, ID> {
    protected MongoTemplateFactory mongoTemplateFactory;

    protected abstract MongoTemplate getMongoTemplate();
    protected abstract Class<T> getBeanClass();


    public AbstractMongoRepository(MappingMongoEntityInformation<T, ID> entityInfo, MongoTemplate mongoTemplate) {
        super(entityInfo, mongoTemplate);
    }
}
