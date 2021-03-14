package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

@Data
@JsonIgnoreProperties
public class ServiceHostConfig extends AbstractServiceConfig {
    private ServerHost serverHost;
    private String serviceName;
    private String version;
}
