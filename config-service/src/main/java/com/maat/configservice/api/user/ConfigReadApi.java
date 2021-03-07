package com.maat.configservice.api.user;

import com.maat.configservice.beans.ServerConfig;
import com.maat.configservice.service.ServerConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConfigReadApi {
    private ServerConfigService serverConfigService;

    @GetMapping("/")
    @Produces("application/json")
    public List<ServerConfig> fetchAllConfigs() {
        return serverConfigService.findAllConfigurations();
    }

    @GetMapping("/{type}")
    @Produces("application/json")
    public List<ServerConfig> findConfigurationForType(@PathParam("type") String type) {
        log.error("still being called to fetch config for type {}",type);
        return serverConfigService.findConfigListForType( type);
    }

}
