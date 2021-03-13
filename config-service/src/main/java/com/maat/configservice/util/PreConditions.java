package com.maat.configservice.util;

import com.google.common.base.Strings;

import java.util.Collection;
import java.util.Map;

public class PreConditions {
  private PreConditions() {}

  public static <K, V> Map<K, V> isNotEmpty(Map<K, V> map) {
    return isNotEmpty(map, "Map cannot be empty or null");
  }

  public static <K, V> Map<K, V> isNotEmpty(Map<K, V> map, String errorMessage) {
    if (map == null || map.size() <= 0) {

      throw new RuntimeException(errorMessage);
    }
    return map;
  }

  public static <E, T extends Collection<E>> T isNotEmpty(T collection) {
    return isNotEmpty(collection, "Cannot be empty or null");
  }

  public static <E, T extends Collection<E>> T isNotEmpty(T collection, String errorMessage) {
    if (collection == null || collection.size() <= 0) {
      throw new RuntimeException(errorMessage);
    }
    return collection;
  }

  public static <T> T ensureType(Object obj, Class<T> expectedType) {
    return ensureType(obj, expectedType, "");
  }

  public static <T> T ensureType(Object obj, Class<T> expectedType, String msg) {
    if (obj == null) {
      return null;
    }
    msg = blankIfNull(msg);
    if (expectedType.isAssignableFrom(obj.getClass())) {
      return (T) obj;
    } else if (Number.class.isAssignableFrom(expectedType)) {
      if (obj instanceof Number) {
        Number number = (Number) obj;
        if (expectedType == Byte.class) {
          return (T) Byte.valueOf(number.byteValue());
        } else if (expectedType == Short.class) {
          return (T) Short.valueOf(number.shortValue());
        } else if (expectedType == Integer.class) {
          return (T) Integer.valueOf(number.intValue());
        } else if (expectedType == Long.class) {
          return (T) Long.valueOf(number.longValue());
        } else if (expectedType == Float.class) {
          return (T) Float.valueOf(number.floatValue());
        } else if (expectedType == Double.class) {
          return (T) Double.valueOf(number.doubleValue());
        }
      }
    }
    throw new RuntimeException(
        msg
            + ": Unexpected type - expected: '"
            + expectedType.getName()
            + "', "
            + "actual: '"
            + obj.getClass().getName()
            + "'");
  }

  public static String blankIfNull(String input) {
    if (input == null) {
      return "";
    }
    return input;
  }

  public static <T extends Collection> T notEmpty(T coll) {
    return notEmpty(coll, "");
  }

  public static <T extends Collection> T notEmpty(T coll, String msg) {
    if (coll == null || coll.size() <= 0) {
      if (Strings.isNullOrEmpty(msg)) {
        msg = "Collection cannot be empty";
      }
      throw new RuntimeException(msg);
    }
    return coll;
  }

  public static <T extends Collection> T notEmptyAndNotNullElements(T coll) {
    return notEmptyAndNotNullElements(coll, "");
  }

  public static <T extends Collection> T notEmptyAndNotNullElements(T coll, String msg) {
    msg = msg == null ? "" : msg;
    if (coll == null || coll.size() <= 0) {
      if (Strings.isNullOrEmpty(msg)) {
        msg = "Collection cannot be empty";
      }
      throw new RuntimeException(msg);
    }
    int index = 0;
    for (Object o : coll) {
      notNull(o, "Null element in position: " + index + " " + msg);
      index++;
    }
    return coll;
  }

  public static void isTrue(boolean expression, String msg) {
    if (!expression) {
      throw new RuntimeException(msg);
    }
  }

  public static void isFalse(boolean expression, String msg) {
    if (expression) {
      throw new RuntimeException(msg);
    }
  }

  public static String notBlank(String value) {
    return notBlank(value, null);
  }

  public static String notBlank(String value, Object msg) {
    if (Strings.isNullOrEmpty(value)) {
      return value;
    }
    throw new RuntimeException(msg == null ? "Cannot be blank" : String.valueOf(msg));
  }

  public static void isBlank(String value, String msg) {
    if (Strings.isNullOrEmpty(value)) {
      return;
    }
    throw new RuntimeException(msg == null ? "Must be blank" : String.valueOf(msg));
  }

  public static <T> void isNull(T instance, String msg) {
    if (instance == null) {
      return;
    }
    if (Strings.isNullOrEmpty(msg)) {
      msg = "Should be null";
    }
    throw new RuntimeException(msg);
  }

  public static <T> T notNull(T instance, String msg) {
    if (instance != null) {
      return instance;
    }
    if (Strings.isNullOrEmpty(msg)) {
      msg = "Cannot be null";
    }
    throw new RuntimeException(msg);
  }

  public static <T> T notNull(T instance) {
    if (instance != null) {
      return instance;
    }
    throw new RuntimeException("Cannot be null");
  }

  public static <T> T[] notEmptyArray(T[] values) {
    return notEmptyArray(values, "Field");
  }

  public static <T> T[] notEmptyArray(T[] values, Object msg) {
    if (values == null || values.length <= 0) {
      throw new RuntimeException(msg + " - cannot be empty array");
    }
    return values;
  }
}
