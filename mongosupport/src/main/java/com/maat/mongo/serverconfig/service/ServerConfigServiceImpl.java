package com.maat.mongo.serverconfig.service;

import com.google.common.cache.LoadingCache;
import com.maat.mongo.serverconfig.beans.MaatConfigProperties;
import com.maat.mongo.serverconfig.beans.ServerConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerConfigServiceImpl implements ServerConfigService {
  private final MaatConfigProperties maatConfigProperties;
  private LoadingCache<ConfigCacheKey, ServerConfig> configCache;
  private final ExecutorService executor;

  public ServiceConfigServiceImpl(
      MaatConfigProperties maatConfigProperties,
      @Qualifier("configExecutor") ExecutorService executor) {
    this.maatConfigProperties = maatConfigProperties;
    this.executor = executor;
  }

  @Data
  @AllArgsConstructor
  @Builder
  private static class ConfigCacheKey {
    private String type;
    private String moduleName;
  }

  @PostConstruct
  public void init() {
    configCache = createLoadingCache();
    setConfigClientProperties();
  }

  @Override
  public ServerConfig findServerConfig(String type, String moduleName) {
    final ConfigCacheKey key =
            ConfigCacheKey.builder()
                    .type(type)
                    .moduleName(moduleName)
                    .build();
    try {
      return configCache.get(key);
    } catch (ExecutionException e) {
      e.printStackTrace();
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
