package com.maat.configservice.repo;

import com.maat.configservice.beans.HostConfig;

import java.util.Collection;
import java.util.List;

public interface HostConfigRepo {
    HostConfig findHostConfigById(String hostConfigId);
    List<HostConfig> findHostConfigsByIds(Collection<String> ids);
    void saveHostConfig(HostConfig hostConfig);
    void deleteById(String id);
    List<HostConfig> findAllHostConfigs();

}
