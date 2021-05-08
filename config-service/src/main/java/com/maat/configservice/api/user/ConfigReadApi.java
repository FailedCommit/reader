package com.maat.configservice.api.user;

import com.maat.configservice.service.ServerConfigService;
import com.maat.servicecommons.serverconfig.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConfigReadApi {
    private final ServerConfigService serverConfigService;

    @GetMapping("/all")
    public List<ServerConfig> fetchAllConfigs() {
        return serverConfigService.findAllConfigurations();
    }

    @GetMapping("/{type}")
    public List<ServerConfig> findConfigurationForType(@PathVariable("type") String type) {
        log.error("still being called to fetch config for type {}",type);
        return serverConfigService.findConfigListForType( type);
    }
}
