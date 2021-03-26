package com.maat.mongo;

import com.maat.mongo.utils.MongoUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

public class BaseGlobalBaseRepository<T, ID> extends AbstractMongoRepository<T, ID> implements MBaseRepository<T,ID> {
  private final String moduleName;
  private final Class<T> beanClass;


  public BaseGlobalBaseRepository(
      String moduleName, Class<T> beanClass, MongoTemplateFactory mongoTemplateFactory) {
    this(
        MongoUtils.createMongoEntityInfo(beanClass),
        mongoTemplateFactory.getGlobalMongoForModule(moduleName),
        moduleName,
        beanClass);
  }

  BaseGlobalBaseRepository(
      MongoEntityInformation<T, ID> info,
      MongoOperations mongoOps,
      String moduleName,
      Class<T> beanClass) {
    super(info, mongoOps);
    beanClass = info.getJavaType();
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
