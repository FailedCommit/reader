package com.maat.configservice.repo;

import com.maat.configservice.beans.HostConfig;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

public class HostConfigRepoMongoImpl implements HostConfigRepo {
    private final MongoTemplate mongoTemplate;

    public HostConfigRepoMongoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public HostConfig findHostConfigById(String hostConfigId) {
        Criteria criteria = Criteria.where("id").is(hostConfigId);
        return mongoTemplate.findOne(Query.query(criteria),HostConfig.class);
    }

    @Override
    public List<HostConfig> findHostConfigsByIds(Collection<String> ids) {
        Criteria criteria = Criteria.where("id").in(ids);
        return mongoTemplate.find(Query.query(criteria),HostConfig.class);
    }

    @Override
    public void saveHostConfig(HostConfig hostConfig) {
        mongoTemplate.save(hostConfig);
    }

    @Override
    public void deletebyId(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        mongoTemplate.remove(Query.query(criteria),HostConfig.class);
    }

    @Override
    public List<HostConfig> findAllHostConfigs() {
        return mongoTemplate.find(new Query(),HostConfig.class);
    }
}
