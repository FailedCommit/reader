package com.maat.servicecommons.serverconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("maat.config")
public class MaatConfigProperties {
    private long configRefreshTime = 60L;
    private long readTimeOut = 10L;
    private long connectTimeOut = 10L;
    private int minIdleConnections = 5;
    private int maxIdleConnections = 60;
    private int stayAliveTime = 90;
    private String poolName = "maat-default-pool";
    private int corePoolSize = 10;
    private int maxPoolSize = 120;
    private long configExpiryTime = 5L;
}
