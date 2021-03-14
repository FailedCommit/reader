package com.maat.mongo.serverconfig.beans;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to sdk-core library to avoid duplication

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("maat.config")
public class MaatConfigProperties {
    private long configRefreshTimeInMinutes = 1L;
    private long readTimeOutInSeconds = 10L;
    private long connectTimeOutInSeconds = 10L;
    private int minIdleConnections = 5;
    private int maxIdleConnections = 60;
    private int stayAliveTimeInSeconds = 90;
    private String poolName = "maat-default-pool";
    private int corePoolSize = 10;
    private int maxPoolSize = 120;
    private long configExpireTimeInMinutes = 600L;
}
