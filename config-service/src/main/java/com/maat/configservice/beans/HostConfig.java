package com.maat.configservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HostConfig {
    private String id;
    private String name;
    @NotNull
    private String type;
    @NotNull
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
