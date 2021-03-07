package com.maat.configservice.repo;

import com.maat.configservice.beans.HostConfig;

import java.util.Collection;
import java.util.List;

public interface HostConfigRepo {
    public HostConfig findHostConfigById(String hostConfigId);
    public List<HostConfig> findHostConfigsByIds(Collection<String> ids);
    public void saveHostConfig(HostConfig hostConfig);
    public void deletebyId(String id);
    List<HostConfig> findAllHostConfigs();

}
