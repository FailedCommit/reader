package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MongoServerConfig.class, name = "MONGO"),
  @JsonSubTypes.Type(value = ServiceHostConfig.class, name = "SERVICE")
})
public class AbstractServiceConfig {
  private String id;

  /**
   * Type as in MONGO, ES etc. {@link ServerType}. Keeping it String so that it's flexible without
   * requiring code change. Backing enum {@link ServerType} will ensure validity of the string value.
   */
  private String type;

  /**
   * {@link ModuleName} defines mostly module but it's flexible to be used to identify a
   * feature/module.
   *
   * <p>Example: READER_DEFAULT category with type {@link ServerType} MONGO will give the config for
   * default mongo database specific to reader app.
   *
   * <p>USER category with type {@link ServerType} ES will give the ElasticSearch config for
   * user app.
   */
  private String moduleName;

  private boolean deleted;
}
