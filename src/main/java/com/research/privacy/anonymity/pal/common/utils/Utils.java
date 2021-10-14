package com.research.privacy.anonymity.pal.common.utils;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@NoArgsConstructor
public class Utils {

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String) value).trim().length() == 0;
        } else if (value instanceof Collection) {
            return ((Collection) value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else {
            return value.getClass().isArray() && Array.getLength(value) == 0;
        }
    }


    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static <T> List<T> safeList(List<T> collection) {
        return isNotEmpty(collection) ? collection : new ArrayList();
    }

    @SafeVarargs
    public static <T> List<T> asList(T... list) {
        if (list != null && list.length == 1 && list[0] == null) {
            return new ArrayList();
        } else {
            List<T> result = new ArrayList();
            if (isNotEmpty(list)) {
                result.addAll(Arrays.asList(list));
            }

            return result;
        }
    }
}
