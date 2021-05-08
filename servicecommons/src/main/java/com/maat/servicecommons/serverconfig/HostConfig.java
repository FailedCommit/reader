package com.maat.servicecommons.serverconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HostConfig {
    private String id;
    private String name;
    private String type;
    private String clusterName;
    private List<ServerHost>hosts;
    private String userName;
    private char[] password;
    private boolean sslEnabled;
    private Boolean replicaSet;                     // mongo replica set
    private String authType;
    private String accessKey;
    private String secret;
    private String awsRegion;
    private String kmsRegion;
    private String keyArn;
    private Map<String,List<String>> additional;
    //TODO: This should be in mongosupport lib
    private MongoClientOptions mongoClientOptions;
}
