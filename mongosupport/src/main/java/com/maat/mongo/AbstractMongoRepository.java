package com.maat.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;


public abstract class AbstractMongoRepository<T, ID> extends SimpleMongoRepository<T, ID> {
    protected MongoTemplateFactory mongoTemplateFactory;

    protected abstract MongoTemplate getMongoTemplate();
    protected abstract Class<T> getBeanClass();


    public AbstractMongoRepository(MongoEntityInformation<T, ID> entityInfo, MongoOperations mongoTemplate) {
        super(entityInfo, mongoTemplate);
    }

    @Override
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }
}
