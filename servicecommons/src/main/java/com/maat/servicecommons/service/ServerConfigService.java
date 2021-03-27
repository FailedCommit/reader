package com.maat.servicecommons.service;

import com.maat.servicecommons.serverconfig.ServerConfig;

import java.util.Collection;

public interface ServerConfigService {
    ServerConfig findServerConfig(String type, String moduleName);
    boolean forceEvictConfig(boolean invalidateAll, Collection<String> invalidationType);
}
