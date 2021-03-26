package com.maat.mongo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.getenv;
import static org.springframework.util.StringUtils.isEmpty;

@Configuration
@ConfigurationProperties(value = "maat.mongo.client")
@Data
@NoArgsConstructor
public class DefaultMongoClientOptions {
    private String applicationName = getApplicationName();
    private int minConnectionsPerHost = 10;
    private int maxConnectionsPerHost = 100;
    private int connectTimeoutInMillis = 60 * 1000;
    private int threadsAllowedToBlockForConnectionMultiplier = 50;

    private static String getApplicationName() {
        String applicationName = getenv("service_name");
        if (isEmpty(applicationName)) applicationName = "MAAT_READER_APP";
        return applicationName;
    }
}
