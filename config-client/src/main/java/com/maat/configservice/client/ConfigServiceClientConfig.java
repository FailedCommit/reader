package com.maat.configservice.client;

import com.maat.servicecommons.serverconfig.ServerConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Configuration
@EnableFeignClients
public class ConfigServiceClientConfig {
    @FeignClient(value = "configClient", url = "http://localhost:8080/configs/")
    public interface ConfigServiceClient {

        @RequestMapping(method = GET, value = "/u/all")
        List<ServerConfig> fetchAllConfigs();
    }
}
