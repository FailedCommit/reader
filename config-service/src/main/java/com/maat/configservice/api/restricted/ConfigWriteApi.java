package com.maat.configservice.api.restricted;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.maat.configservice.beans.CustomProperties;
import com.maat.configservice.beans.HostConfig;
import com.maat.configservice.beans.ServerConfig;
import com.maat.configservice.service.HostConfigurationService;
import com.maat.configservice.service.ServerConfigService;
import com.maat.configservice.util.PreConditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static com.maat.configservice.util.CollectionUtils.nullSafeMap;
import static java.util.Objects.isNull;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConfigWriteApi {
  private final HostConfigurationService hostConfigService;
  private final ServerConfigService serverConfigService;

  @PostMapping("/host")
  public HostConfig createHostConfig(@RequestBody HostConfig hostConfig) {
    return hostConfigService.createHostConfig(hostConfig);
  }

  @PutMapping("/host/{id}")
  public HostConfig updateHostConfig(
          @PathParam("id") String id,
          @RequestBody HostConfig hostConfig) {
    return hostConfigService.updateHostConfig(id, hostConfig);
  }

  @PostMapping("/server")
  public ServerConfig createServerConfig(
          @RequestBody ServerConfig serverConfig,
          @RequestParam("override") boolean override) {
    return serverConfigService.saveConfig(serverConfig, override);
  }

  @PostMapping("/server/{type}")
  public ServerConfig createServerConfigByType(
      @PathParam("type") String type,
      @RequestBody ServerConfig serverConfig,
      @RequestParam("override") boolean override) {
    PreConditions.isTrue(
        Strings.isNullOrEmpty(serverConfig.getType()) || serverConfig.getType().equals(type),
        "Invalid type info");
    return serverConfigService.saveConfig(serverConfig, override);
  }

  @PutMapping("/server/{type}/module/{moduleName}/property")
  public void addProperty(
      @PathParam("type") String type,
      @PathParam("moduleName") String module,
      @RequestParam CustomProperties customProperties) {

    ServerConfig existing = serverConfigService.findConfig(type, module);
    PreConditions.notNull(existing, "no config found");

    if (isNull(existing.getProperties())) {
      existing.setProperties(new CustomProperties());
    }
    Map<String, List<String>> updatedProperties = Maps.newHashMap();
    updatedProperties.putAll(nullSafeMap(nullSafeMap(existing.getProperties().getProperties())));
    updatedProperties.putAll(nullSafeMap(customProperties.getProperties()));
    existing.getProperties().setProperties(updatedProperties);
    serverConfigService.saveConfig(existing, true);
  }
}
