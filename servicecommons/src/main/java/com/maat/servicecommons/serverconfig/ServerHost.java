package com.maat.servicecommons.serverconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerHost {
    private String id;
    private String host;
    private int port;
    private String protocol;
}
