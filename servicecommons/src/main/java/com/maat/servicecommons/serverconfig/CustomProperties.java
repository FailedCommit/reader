package com.maat.servicecommons.serverconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomProperties {
  public enum KnownPropertyKey {
    corsAllowedHeaders,
    corsAllowedOrigins,
    corsAllowedMethods,
    corsExposedHeaders,
    checkDMSUserForAuth,
    configAllowedViaRestricted,
    gmInvoiceUrl,
    gmServicesUrl,
    gmServiceLaneUrl,
    tcpUrl,
    tdpUrl,
    @Deprecated
    corsAllowedDomains;
  }

  private Map<String, List<String>> properties = new HashMap<>();

  private static <K, V> V safeGet(Map<K, V> map, K key) {
    return CollectionUtils.isEmpty(map) ? null : map.get(key);
  }
}
