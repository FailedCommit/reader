package com.maat.configservice.service;

import com.google.common.collect.Lists;
import com.maat.configservice.beans.HostConfig;
import com.maat.configservice.beans.ServerHost;
import com.maat.configservice.beans.ServerType;
import com.maat.configservice.repo.HostConfigRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.maat.core.utils.MPreConditions.isTrue;
import static com.maat.core.utils.MPreConditions.notNull;

@Service
@AllArgsConstructor
public class HostConfigurationService {
  private final HostConfigRepo hostConfigRepo;

  public HostConfig createHostConfig(HostConfig config) {
    validateNewHostConfig(config);
    hostConfigRepo.saveHostConfig(config);
    return config;
  }

  public HostConfig updateHostConfig(String id, HostConfig config) {
    validateHostConfigForUpdate(id, config);
    hostConfigRepo.saveHostConfig(config);
    return config;
  }

  public List<HostConfig> findAllHostsByIds(List<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return Lists.newArrayList();
    }
    return hostConfigRepo.findHostConfigsByIds(ids);
  }

  public HostConfig findHostById(String id) {
    return hostConfigRepo.findHostConfigById(id);
  }

  private void validateHostConfigForUpdate(String id, HostConfig config) {
    notNull(id, "id is null");
    if (config.getId() == null) {
      config.setId(id);
    } else {
      isTrue(config.getId().equals(id), "Cannot update the Id of the host");
    }
    validateHostConfig(config);
    if (config.getId() != null) {
      HostConfig existing = hostConfigRepo.findHostConfigById(config.getId());
      if (existing == null) {
        throw new RuntimeException("Config not found");
      }
      isTrue(
          existing.getType().equals(config.getType()),
          "Cannot change the host type. Existing is " + existing.getType());
    }
  }

  private void validateNewHostConfig(HostConfig config) {
    validateHostConfig(config);
    if (config.getId() != null) {
      HostConfig existing = hostConfigRepo.findHostConfigById(config.getId());
      if (existing != null) {
        throw new RuntimeException("Create another host with the same id is not allowed");
      }
    }
  }

  private void validateHostConfig(HostConfig config) {
    if (CollectionUtils.isEmpty(config.getHosts())) {
      throw new RuntimeException("empty hosts");
    }
    if (config.getType().equals(ServerType.MONGO.name())) {
      notNull(config.getReplicaSet(), "please tell if this is a replica set");
    }
    for (ServerHost host : config.getHosts()) {
      notNull(host.getHost(), "host is null");
      notNull(host.getPort(), "port is null");
      if (config.getType() == ServerType.ES.name()) {
        notNull(host.getProtocol(), "protocol is null");
      }
    }
  }
}
