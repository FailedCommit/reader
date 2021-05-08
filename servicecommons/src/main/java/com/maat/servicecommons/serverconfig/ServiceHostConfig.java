package com.maat.servicecommons.serverconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ServiceHostConfig extends AbstractServiceConfig {
    private ServerHost serverHost;
    private String serviceName;
    private String version;
}
