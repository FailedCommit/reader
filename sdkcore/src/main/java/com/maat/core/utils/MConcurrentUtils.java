package com.maat.core.utils;

import com.google.common.util.concurrent.Striped;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

@UtilityClass
public class MConcurrentUtils {
  public static final Striped<Lock> sharedStrippedLocks = Striped.lock(200);
  public static final int DEFAULT_LOCK_TIMEOUT = 60;

  public static <K, V, P> V getOrCreateDefault(
      Striped<Lock> creationLocks,
      ConcurrentMap<K, V> map,
      K key,
      Factory<K, V, P> factory,
      P factoryParam) {
    return getOrCreate(
        creationLocks, map, key, factory, factoryParam, DEFAULT_LOCK_TIMEOUT, SECONDS);
  }

  private static <V, K, P> V getOrCreate(
      Striped<Lock> creationLocks,
      ConcurrentMap<K, V> map,
      K key,
      Factory<K, V, P> factory,
      P factoryParam,
      long defaultLockTimeout,
      TimeUnit timeUnit) {
    V value = map.get(key);
    if (value == null) {
      boolean acquired = false;
      Lock lock = creationLocks.get(key);
      try {
        acquired = tryLockNoException(lock, defaultLockTimeout, timeUnit);
        if (!acquired) {
          value = map.get(key);
          if (nonNull(value)) {
            map.put(key, value);
          }
        }
      } finally {
        if (acquired) lock.unlock();
      }
    }
    return value;
  }

  private static boolean tryLockNoException(Lock lock, long defaultLockTimeout, TimeUnit timeUnit) {
    try {
      return lock.tryLock(defaultLockTimeout, timeUnit);
    } catch (InterruptedException e) {
      return false;
    }
  }

  public static interface Factory<K, V, P> {
    V create(K key, P param);
  }
}
