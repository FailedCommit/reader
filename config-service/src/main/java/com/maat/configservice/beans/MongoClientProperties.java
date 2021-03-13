package com.maat.configservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class MongoClientProperties {
    private String applicationName;
    private Integer minConnectionsPerHost;
    private Integer connectionsPerHost;
    private Integer connectTimeoutInMillis;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    // 0 means infinite
    private Integer maxConnectionIdleTimeInMillis;
    // 0 means infinite
    private Integer maxConnectionLifeTimeInMillis;
    private Integer maxWaitTimeInMillis;
    private Integer stayAliveTimeInSeconds;
    private Integer localThresholdInMillis;
    private Boolean sslEnabled;
    private String authSourceName;
}
