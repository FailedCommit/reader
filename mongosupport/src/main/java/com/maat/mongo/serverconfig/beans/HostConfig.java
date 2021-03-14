package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HostConfig {
    private String id;
    private String name;
    private String type;
    private String clusterName;
    private List<ServerHost>hosts;
    private String userName;
    private String password;
    private boolean sslEnabled;
    private Boolean replicaSet;                     // mongo replica set
    private String authType;
    private String accessKey;
    private String secret;
    private String awsRegion;
    private String kmsRegion;
    private String keyArn;
    private Map<String,List<String>> additional;
    private MongoClientProperties mongoClientProperties;
}
