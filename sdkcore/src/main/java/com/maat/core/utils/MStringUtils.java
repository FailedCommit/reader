package com.maat.core.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@UtilityClass
public class MStringUtils {
  public static final String EMPTY = "";

  public static String nullSafeString(String s) {
    if (nonNull(s)) return s;
    return EMPTY;
  }

  public static boolean nonBlank(String value) {
    return !isBlank(value);
  }

  public static boolean isBlank(String value) {
    return isNull(value) || value.trim().length() == 0;
  }
}
