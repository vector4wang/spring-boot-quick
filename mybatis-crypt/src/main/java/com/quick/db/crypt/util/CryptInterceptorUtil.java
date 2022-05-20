package com.quick.db.crypt.util;

import java.util.Map;

public class CryptInterceptorUtil {

    public static boolean isNotCrypt(Object o) {
        return o == null || o instanceof Double || o instanceof Integer || o instanceof Long || o instanceof Boolean || o instanceof Map;
    }
}