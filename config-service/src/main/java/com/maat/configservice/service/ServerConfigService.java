package com.maat.configservice.service;

import com.maat.configservice.beans.HostConfig;
import com.maat.configservice.beans.ServerConfig;
import com.maat.configservice.beans.ServerType;
import com.maat.configservice.repo.HostConfigRepo;
import com.maat.configservice.repo.ServerConfigRepo;
import com.maat.configservice.util.ConfigUtils;
import com.maat.core.utils.MCollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.maat.core.utils.MCollectionUtils.nullSafeList;

// TODO: find how to do non consistent read
// TODO: find how to do consistent and non-consistent write.

@Service
public class ServerConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ServerConfigService.class);
    private final ServerConfigRepo serverConfigRepo;
    private final HostConfigRepo hostConfigRepo;
    private ConcurrentHashMap<String, HostConfig> hostCache = null;

    @Autowired
    public ServerConfigService(ServerConfigRepo serverConfigRepo, HostConfigRepo hostConfigRepo) {
        this.serverConfigRepo = serverConfigRepo;
        this.hostConfigRepo = hostConfigRepo;
    }

    public ServerConfig saveConfig(ServerConfig serverConfig, boolean override) {
        serverConfig.setConfigKey(ConfigUtils.createKey(serverConfig));
        validateServerConfig(serverConfig);
        return doCreateServerConfig(serverConfig, override);
    }

    public List<ServerConfig> findConfigListForType(String type) {
        List<ServerConfig> configList = serverConfigRepo.findConfigForType(type);
        return populateHostConfig(configList);
    }

    public List<ServerConfig> findConfigListForTypeAndModule(String type, String module) {
        return serverConfigRepo.findConfigForTypeAndModule(type, module);
    }

    public ServerConfig findConfig(String type, String module) {
        ServerConfig config = serverConfigRepo.findConfig(type, module);
        if (config != null) {
            if (StringUtils.isNotBlank(config.getHostConfigId())) {
                config.setHostConfig(getHostForId(config.getHostConfigId()));
            }
        }
        return config;
    }

    public List<ServerConfig> findAllConfigurations() {
        return serverConfigRepo.findAllConfigurations();
    }

    private <T> List<ServerConfig> populateHostConfig(List<ServerConfig> configList) {
    if (CollectionUtils.isEmpty(configList)) return configList;
        for (ServerConfig config : configList) {
            if (StringUtils.isNotBlank(config.getHostConfigId())) {
                config.setHostConfig(getHostForId(config.getHostConfigId()));
            }
        }
        return configList;
    }

    private ServerConfig doCreateServerConfig(ServerConfig config, boolean override) {

        if (serverConfigRepo.findConfig(
                config.getType(),
                config.getModuleName())
                != null
                && !override) {
            throw new RuntimeException(
                    "serverConfig for requested params already exists and overriding is set to false");
        }
        serverConfigRepo.save(config);
        return config;
    }

    private void validateServerConfig(ServerConfig serverConfig) {
//        validator.validate(serverConfig);
        ServerType type = ServerType.valueOf(serverConfig.getType());
        switch (type) {
            case MONGO:
            case MYSQL:
            case POSTGRES:
                ConfigUtils.notBlank(serverConfig.getDbName(), "db name is blank");
                break;
            case REDIS:
                ConfigUtils.notBlank(serverConfig.getDbName(), "db name is blank");
                ConfigUtils.isTrue(isPositiveInteger(serverConfig.getDbName()), "db name has to be integer");
                break;
            case ES:
                ConfigUtils.notBlank(serverConfig.getReadIndex(), "read index is blank");
                ConfigUtils.notBlank(serverConfig.getWriteIndex(), "write index is blank");
                break;
            case KAFKA:
                break;
        }

        if (type.isHostRequired()) {
            ConfigUtils.notBlank(serverConfig.getHostConfigId(), "Host Config Id is required. Please provide.");
        }
        if (type != ServerType.RESTRICTED_API_ACCESS && type.isHostRequired()) {
            validateHostConfig(serverConfig);
        }
    }

    private boolean isPositiveInteger(String dbName) {
        try {
            int i = Integer.parseInt(dbName);
            return i >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateHostConfig(ServerConfig serverConfig) {
        ConfigUtils.notBlank(serverConfig.getHostConfigId(), "No host config provided");
        HostConfig hostConfigById = hostConfigRepo.findHostConfigById(serverConfig.getHostConfigId());
        if (hostConfigById == null) {
            throw new RuntimeException("hostConfig for given hostConfigId does not exist");
        }
    }

    private HostConfig getHostForId(String id) {
        HostConfig config = getHostConfigCache().get(id);
        if (config == null) {
            config = hostConfigRepo.findHostConfigById(id);
            if (config != null) {
                hostCache.put(config.getId(), config);
            } else {
                logger.error("No host found for id " + id);
            }
        }
        return config;
    }

    private ConcurrentHashMap<String, HostConfig> getHostConfigCache() {
        if (hostCache == null) {
            synchronized (this) {
                if (hostCache == null) {
                    List<HostConfig> allHostConfigs = hostConfigRepo.findAllHostConfigs();
                    hostCache = new ConcurrentHashMap<>();
                    for (HostConfig config : nullSafeList(allHostConfigs)) {
                        hostCache.put(config.getId(), config);
                    }
                }
            }
        }
        return hostCache;
    }
}
