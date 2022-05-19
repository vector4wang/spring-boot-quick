package com.quick.db.crypt.util;

public class CryptInterceptorUtil {

    public static boolean isNotCrypt(Object o) {
        return o == null || o instanceof Double || o instanceof Integer || o instanceof Long || o instanceof Boolean;
    }
}