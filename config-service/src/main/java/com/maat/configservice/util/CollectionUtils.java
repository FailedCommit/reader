package com.maat.configservice.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> nullSafeList(List<T> c){
        return c != null?c:new ArrayList<T>();
    }
}
