package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class MongoClientOptions {
    private String applicationName;
    private Integer minConnectionsPerHost;
    private Integer connectionsPerHost;
    private Integer connectTimeoutInMillis;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    //0 means infinite
    private Integer maxConnectionIdleTimeInMillis;
    //0 means infinite
    private Integer maxConnectionLifeTimeInMillis;
    private Integer maxWaitTimeInMillis;
    private Integer stayAliveTimeInSeconds;
    private Integer localThresholdInMillis;
    private Boolean sslEnabled;
    private String authSourceName;

}
