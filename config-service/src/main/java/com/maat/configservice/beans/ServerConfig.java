package com.maat.configservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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
  @NotNull private String type;

  @NotNull
  @Field("id")
  @Indexed(unique = true)
  private String configKey;

  @NotNull private String moduleName;
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
