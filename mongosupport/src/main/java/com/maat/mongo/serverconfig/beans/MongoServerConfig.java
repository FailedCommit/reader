package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

/** Ideally, either one of the hosts or url should be provided. If both are provided then url will be used. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MongoServerConfig extends AbstractServiceConfig {
    private List<ServerHost> hosts;
    private String url;
    private String dbName;
    private boolean replicaSet;
}
