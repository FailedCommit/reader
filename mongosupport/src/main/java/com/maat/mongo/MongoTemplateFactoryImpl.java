package com.maat.mongo;

import com.maat.core.utils.MConcurrentUtils.Factory;
import com.maat.core.utils.MPreConditions;
import com.maat.mongo.serverconfig.beans.ServerConfig;
import com.maat.mongo.serverconfig.beans.ServerType;
import com.maat.mongo.serverconfig.service.ServerConfigService;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.maat.core.utils.MConcurrentUtils.getOrCreate;
import static com.maat.core.utils.MConcurrentUtils.sharedStrippedLocks;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
@Slf4j
@RequiredArgsConstructor
public class MongoTemplateFactoryImpl implements MongoTemplateFactory {
  private final MongoClientFactory clientFactory;
  private final ServerConfigService serverConfigService;
  private final Factory<String, MongoTemplate, ServerConfig> MONGO_TEMPLATE_FACTORY =
      (key, config) -> createMongoTemplate(config);
  private final ConcurrentMap<String, MongoTemplate> mongoTemplateMap = new ConcurrentHashMap<>();

  @Override
  public MongoTemplate getGlobalMongoForModule(String moduleName) {
    ServerConfig serverConfig =
        serverConfigService.findServerConfig(ServerType.MONGO.name(), moduleName);
    MPreConditions.notNull(serverConfig, "No Config found for key: " + moduleName);
    return getOrCreate(
        sharedStrippedLocks,
        mongoTemplateMap,
        moduleName,
        MONGO_TEMPLATE_FACTORY,
        serverConfig,
        2,
        MINUTES);
  }

  private MongoTemplate createMongoTemplate(ServerConfig serverConfig) {
    MongoClient mongoClient = clientFactory.getMongoClient(serverConfig);
    return new MongoTemplate(mongoClient, serverConfig.getDbName());
  }
}
