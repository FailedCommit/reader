package com.maat.mongo.serverconfig.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.maat.mongo.serverconfig.beans.MaatConfigProperties;
import com.maat.mongo.serverconfig.beans.ServerConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Slf4j
public class ServerConfigServiceImpl implements ServerConfigService {
  private final MaatConfigProperties maatConfigProperties;
  private final LoadingCache<ConfigCacheWrapper, ServerConfig> configCache;
  private final ExecutorService executor;
  private final RestTemplate restTemplate;
private final ObjectMapper mapper;
  public ServerConfigServiceImpl(
      MaatConfigProperties maatConfigProperties,
      @Qualifier("configExecutor") ExecutorService executor) {
    this.maatConfigProperties = maatConfigProperties;
    this.restTemplate = new RestTemplate();
    this.executor = executor;
    configCache = createLoadingCache();
    this.mapper =  new ObjectMapper();
  }

  @Data
  @AllArgsConstructor
  @Builder
  private static class ConfigCacheWrapper {
    private String type;
    private String moduleName;
  }

  @PostConstruct
  public void init() {
    setConfigClientProperties();
  }

  private LoadingCache<ConfigCacheWrapper, ServerConfig> createLoadingCache() {
    final long configRefreshTime = maatConfigProperties.getConfigRefreshTime();
    final long configExpiryTime = maatConfigProperties.getConfigExpiryTime();
    return CacheBuilder.newBuilder()
        .concurrencyLevel(10)
        .maximumSize(500)
        .refreshAfterWrite(configRefreshTime, MINUTES)
        .expireAfterWrite(configExpiryTime, MINUTES)
        .build(
            new CacheLoader<ConfigCacheWrapper, ServerConfig>() {
              @Override
              @ParametersAreNonnullByDefault
              public ServerConfig load(ConfigCacheWrapper keyWrapper) throws Exception {
                return fetchServerConfigByKey(keyWrapper);
              }

              @Override
              public ListenableFuture<ServerConfig> reload(
                  ConfigCacheWrapper keyWrapper, ServerConfig oldValue) throws Exception {
                ListenableFutureTask<ServerConfig> task =
                    ListenableFutureTask.create(() -> fetchServerConfigByKey(keyWrapper));
                executor.execute(task);
                return task;
              }
            });
  }

  private void setConfigClientProperties() {
    // TODO: Implement me
  }

  private ServerConfig fetchServerConfigByKey(ConfigCacheWrapper wrapper) {
    // TODO: Create a ConfigService client and use use that instead.
    RequestEntity entity = new RequestEntity(HttpMethod.GET, URI.create("http://localhost:8081/configs/u/MONGO"));
   val configs= restTemplate.exchange(entity, new ParameterizedTypeReference<List<ServerConfig>>() {
    }).getBody();
   return configs.get(0);
  }

  @Override
  public ServerConfig findServerConfig(String type, String moduleName) {
    final ConfigCacheWrapper key =
        ConfigCacheWrapper.builder().type(type).moduleName(moduleName).build();
    try {
      return configCache.get(key);
    } catch (ExecutionException e) {
      log.error("Failed to load config from the cache, Exception: {}", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean forceEvictConfig(boolean invalidateAll, Collection<String> invalidationTypes) {
    boolean outcome = false;
    if (invalidateAll) {
      configCache.invalidateAll();
      outcome = true;
    } else if (nonNull(invalidationTypes) && !invalidationTypes.isEmpty()) {
      configCache.invalidateAll(invalidationTypes);
    }
    return outcome;
  }
}
