package com.maat.configservice.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class ConfigProperties {
  private static ConfigProperties instance = new ConfigProperties();
  private Properties properties;
  private boolean localCluster;

  public static ConfigProperties getInstance() {
    return instance;
  }

  private ConfigProperties() {
    Set<String> localClusters = new HashSet<>(Arrays.asList("local", ""));
    String clusterType = getSystemOrEnvProperty("CLUSTER_TYPE");
    if (localClusters.contains(clusterType)) {
      try {
        properties = loadPropertiesFromFile("/mnt1/config/maatGlobal.properties", false);
      } catch (IOException e) {
        log.error(e.getMessage());
        throw new RuntimeException(e);
      }
      localCluster = true;
    }
  }

  public String getGlobalMongoHostPortCsv() {
    if (localCluster) {
      return properties.getProperty("global.mongo.hostportcsv");
    } else {
      return getSystemOrEnvProperty("global_mongo_hostportcsv");
    }
  }

  public String getGlobalMongoDBName() {
    if (localCluster) {
      return properties.getProperty("global.mongo.db");
    } else {
      return getSystemOrEnvProperty("global_mongo_db");
    }
  }

  public String getProperty(String key, String defaultValue) {
    String value = null;
    if (localCluster) {
      value = properties.getProperty(key);
    } else {
      value = getSystemOrEnvProperty(key);
    }
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private static String getSystemOrEnvProperty(String s) {
    String value = System.getenv(s);
    if (value == null) {
      value = System.getProperty(s);
    }
    return value;
  }

  private static Properties loadPropertiesFromFile(String fileName, boolean isResource)
      throws IOException {
    Properties props = null;
    InputStream stream = null;
    try {
      if (isResource) {
        stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
      } else {
        stream = new FileInputStream(fileName);
      }
      props = new Properties();
      props.load(stream);
    } catch (IOException e) {
      throw new RuntimeException("failed to load properties", e);
    } finally {
      stream.close();
    }
    return props;
  }
}
