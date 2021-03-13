package com.maat.configservice.util;

import com.maat.configservice.beans.ServerConfig;
import org.springframework.util.StringUtils;

public class ConfigUtils {
    public static String createKey(ServerConfig serverConfig){
        return createKey(serverConfig.getType(),serverConfig.getModuleName());
    }

    public static String createKey(String type,String module){
        return type + "_" + module;
    }

    public static String notBlank(String value, Object msg) {
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException(msg == null ? "Cannot be blank" : String.valueOf(msg));
        }
        return value;

    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new RuntimeException(msg);
        }
    }
}

