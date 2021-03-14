package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

//TODO: copied from config-service. Move to servicecommons library to avoid duplication

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerHost {
    private String id;
    private String host;
    private int port;
    private String protocol;
}
