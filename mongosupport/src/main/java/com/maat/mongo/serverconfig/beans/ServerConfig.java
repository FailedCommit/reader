package com.maat.mongo.serverconfig.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;


//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerConfig {
  public static final String FIELD_TYPE = "type";
  public static final String FIELD_CATEGORY = "category";

  /** {@link ServerType} */
  private String type;
  @Field("id")
  @Indexed(unique = true)
  private String configKey;

 private String moduleName;
  // mongo && mysql details
  private String dbName;
  private String readIndex;
  private String writeIndex;
  private Map<String, List<String>> additional;
  private String hostConfigId;
  private boolean restrictedEnabled;
  private String serviceHostURL;
  /** this is not used by anyone except restricted access. */
  private Long validUntil;
  // transient to db
  private HostConfig hostConfig;
  private CustomProperties properties;
  private boolean roleBasedEnabled;
}
