package com.maat.configservice.repo;

import com.maat.servicecommons.serverconfig.ServerConfig;

import java.util.List;

public interface ServerConfigRepo {
    void createTable() throws InterruptedException;
    void save(ServerConfig config);
    void delete(ServerConfig serverConfig);
    ServerConfig findConfig(String type, String moduleName);
    List<ServerConfig> findConfigForType(String type);
    List <ServerConfig> findConfigForTypeAndModule(String type, String moduleName);
    List <ServerConfig> findAllConfigurations();
}
