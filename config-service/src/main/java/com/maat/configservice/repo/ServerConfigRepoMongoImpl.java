package com.maat.configservice.repo;

import com.maat.configservice.beans.ServerConfig;
import com.maat.configservice.util.ConfigUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Objects;

import static com.maat.configservice.util.ConfigUtils.notBlank;

public class ServerConfigRepoMongoImpl implements ServerConfigRepo {
  private final MongoTemplate mongoTemplate;

  public ServerConfigRepoMongoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void createTable() throws InterruptedException {}

  @Override
  public void save(ServerConfig config) {
    final Criteria criteria = Criteria.where("id").is(config.getConfigKey());
    final Document convert = (Document) mongoTemplate.getConverter().convertToMongoType(config);
    final Update update = Update.fromDocument(Objects.requireNonNull(convert), "_id");
    mongoTemplate.upsert(Query.query(criteria), update, ServerConfig.class);
  }

  @Override
  public void delete(ServerConfig serverConfig) {
    String configKey = serverConfig.getConfigKey();
    Criteria criteria = Criteria.where("id").is(configKey);
    mongoTemplate.remove(Query.query(criteria), ServerConfig.class);
  }

  @Override
  public ServerConfig findConfig(
      String type, String moduleName) {
    notBlank(type, "type not provided");
    notBlank(moduleName, "module not provided");
    String key = ConfigUtils.createKey(type, moduleName);
    Criteria criteria = Criteria.where("id").is(key);
    return mongoTemplate.findOne(Query.query(criteria), ServerConfig.class);
  }

  @Override
  public List<ServerConfig> findConfigForType(String type) {
    Criteria criteria = Criteria.where("type").is(type);
    return mongoTemplate.find(Query.query(criteria), ServerConfig.class);
  }

  @Override
  public List<ServerConfig> findConfigForTypeAndModule(String type, String moduleName) {
    return null;
  }

  @Override
  public List<ServerConfig> findAllConfigurations() {
    return mongoTemplate.find(new Query(), ServerConfig.class);
  }
}
