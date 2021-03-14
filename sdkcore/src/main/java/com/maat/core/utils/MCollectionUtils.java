package com.maat.core.utils;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class MCollectionUtils {
    public static <T> List<T> nullSafeList(List<T> c){
        return c != null?c:new ArrayList<T>();
    }

    public static  <K,V> Map<K, V> nullSafeMap(Map<K,V>m){
        return m!=null?m:new HashMap<K,V>();
    }

    public static final <T> boolean isEmpty(Collection<T> c){
        return !isNotEmpty(c);
    }

    public static <T> boolean isNotEmpty(Collection<T>c){
        return c != null && c.size() > 0;
    }

}