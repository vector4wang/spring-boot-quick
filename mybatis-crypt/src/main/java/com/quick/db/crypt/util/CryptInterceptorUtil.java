package com.quick.db.crypt.util;

import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CryptInterceptorUtil {


    /**
     * 对象名称缓存<obj fqn,list<field>>  对对象名和对象里的属性加解密的时候可以从缓存中直接读取
     */
    public static final ConcurrentHashMap<String, List<String>> ENTITY_FILED_ANN_MAP = new ConcurrentHashMap<>();


    public static boolean isNotCrypt(Object o) {
        return o == null || o instanceof Double || o instanceof Integer || o instanceof Long || o instanceof Boolean || o instanceof Map;
    }

    public static boolean isWriteCmd(SqlCommandType commandType) {
        return SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.INSERT.equals(commandType);

    }
}