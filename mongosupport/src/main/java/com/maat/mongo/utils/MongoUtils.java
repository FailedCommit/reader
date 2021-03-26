package com.maat.mongo.utils;

import com.maat.mongo.MongoTemplateFactory;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.util.ClassTypeInformation;

@UtilityClass
public class MongoUtils {
    public static <T,ID> MappingMongoEntityInformation<T, ID> createMongoEntityInfo(Class<T> beanClass) {
        final ClassTypeInformation<T> from = ClassTypeInformation.from(beanClass);
        final BasicMongoPersistentEntity<T> properties = new BasicMongoPersistentEntity<>(from);
        final MappingMongoEntityInformation<T, ID> info = new MappingMongoEntityInformation<>(properties);
        return info;
    }

    public static MongoTemplate getGlobalMongoForModule(MongoTemplateFactory factory, String moduleName) {
        return factory.getGlobalMongoForModule(moduleName);
    }
}
