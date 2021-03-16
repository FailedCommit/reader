package com.maat.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class BaseGlobalMongoRepository<T, ID> extends AbstractMongoRepository<T, ID> {
    private final String moduleName;
    private final Class<T> beanClass;
    private final MappingMongoEntityInformation<T, ID> entityInfo;
    private final MongoTemplate mongoTemplate;

    public BaseGlobalMongoRepository(
            String moduleName,
            Class<T> beanClass,
            MappingMongoEntityInformation<T, ID> entityInfo,
            MongoTemplate mongoTemplate) {
        super(entityInfo, mongoTemplate);
        this.entityInfo = entityInfo;
        this.mongoTemplate = mongoTemplate;
        this.moduleName = moduleName;
        this.beanClass = beanClass;
    }

    @Override
    protected MongoTemplate getMongoTemplate() {
        return mongoTemplateFactory.getGlobalMongoForModule(moduleName);
    }

    @Override
    protected Class<T> getBeanClass() {
        return beanClass;
    }
}
