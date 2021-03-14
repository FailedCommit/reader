package com.maat.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class BaseGlobalMongoRepository<T, ID> extends AbstractMongoRepository<T, ID> {
    private final String moduleName;
    private final Class<T> beanClass;
    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;

    public BaseGlobalMongoRepository(
            String moduleName,
            Class<T> beanClass,
            MongoEntityInformation<T, ID> entityInformation,
        MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);
        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
        this.moduleName = moduleName;
        this.beanClass = beanClass;
    }

    @Override
    protected MongoTemplate getMongoTemplate() {
        return mongoTemplateFactory.getGlobalMongoForModule(moduleName);
    }

    @Override
    protected Class<T> getBeanClass() {
        return null;
    }
}
