package com.quick.db.crypt.intercept;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.PluginUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * https://blog.csdn.net/qq_42764269/article/details/121121807
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {java.sql.Statement.class})
})
public class CryptReadInterceptor implements Interceptor {

    private static final String MAPPED_STATEMENT = "mappedStatement";

    private Encrypt encrypt;

    public CryptReadInterceptor(Encrypt encrypt) {
        Objects.requireNonNull(encrypt, "encrypt should not be null!");
        this.encrypt = encrypt;
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final List<Object> results = (List<Object>) invocation.proceed();
        if (results.isEmpty()) {
            return results;
        }

        final ResultSetHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        final MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        final MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
        final ResultMap resultMap = mappedStatement.getResultMaps().isEmpty() ? null : mappedStatement.getResultMaps().get(0);

        Object result = results.get(0);
        CryptEntity cryptEntity = result.getClass().getAnnotation(CryptEntity.class);
        if (cryptEntity == null) {
            return results;
        }

        Map<String, CryptField> cryptFieldMap = getCryptField(resultMap);
        if (cryptFieldMap.isEmpty()) {
            return results;
        }

        for (Object obj : results) {
            MetaObject objMetaObject = mappedStatement.getConfiguration().newMetaObject(obj);
            for (Map.Entry<String, CryptField> entry : cryptFieldMap.entrySet()) {
                String name = entry.getKey();
                String value = (String) objMetaObject.getValue(name);
                if (value != null) {
                    // 解密
                    String encrypted = this.encrypt.decrypt(value);
                    objMetaObject.setValue(name, encrypted);
                }
            }
        }
        return results;
    }

    // ResultMap ?
    private Map<String, CryptField> getCryptField(ResultMap resultMap) {
        Map<String, CryptField> cryptFieldMap = new HashMap<>(16);

        if (resultMap == null) {
            return cryptFieldMap;
        }
        Class<?> clazz = resultMap.getType();
        for (Field declaredField : clazz.getDeclaredFields()) {
            CryptField cryptField = declaredField.getAnnotation(CryptField.class);
            if (cryptField != null) {
                cryptFieldMap.put(declaredField.getName(), cryptField);
            }
        }

        return cryptFieldMap;
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}