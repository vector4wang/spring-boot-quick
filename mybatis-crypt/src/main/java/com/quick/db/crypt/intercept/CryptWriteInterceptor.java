package com.quick.db.crypt.intercept;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import com.quick.db.crypt.encrypt.AesDesEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.CryptInterceptorUtil;
import com.quick.db.crypt.util.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.*;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class CryptWriteInterceptor implements Interceptor {

    /**
     * org.apache.ibatis.reflection.ParamNameResolver#getNamedParams(java.lang.Object[])
     * 跳过无用的paramx
     */
    private static final String PARAM = "param";
    private static final String MAPPEDSTATEMENT = "delegate.mappedStatement";
    private static final String BOUND_SQL = "delegate.boundSql";

    private Encrypt encrypt;

    public CryptWriteInterceptor(Encrypt encrypt) {
        if (Objects.isNull(encrypt)) {
            try {
                encrypt = new AesDesEncrypt("0123avb");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        this.encrypt = encrypt;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPEDSTATEMENT);

        log.info("mappedStatement.getId(): {}", mappedStatement.getId());

        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        /**
         * 只有insert和update的才继续
         */
        if (!isWriteCmd(commandType)) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue(BOUND_SQL);
        Object params = boundSql.getParameterObject();
        /**
         * params 单个参数 insert(user)     Object
         * params 集合参数 insert(list<user>)  MapperMethod$ParamMap
         *https://github.com/miaoxinwei/mybatis-crypt/blob/master/src/main/java/org/apache/ibatis/plugin/CryptInterceptor.java
         */
        log.info("params.getClass().getTypeName(): {}", params.getClass().getTypeName());

        /**
         * MapperMethod.ParamMap
         * 参数使用@param，程序会自动增加对应的param1
         *
         */
        if (params instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) params;
            for (Map.Entry<String, Object> paramObj : paramMap.entrySet()) {
                Object paramValue = paramObj.getValue();
                if (CryptInterceptorUtil.isNotCrypt(paramValue) || paramValue instanceof Map || paramObj.getKey().contains("param")) {
                    continue;
                }
                if (paramValue instanceof List) {
                    listCrypt((List) paramValue);
                }
            }
        } else if (params instanceof Map) {
            return invocation.proceed();
        }




        CryptEntity cryptEntity = params != null ? params.getClass().getAnnotation(CryptEntity.class) : null;
        if (cryptEntity != null) {
            handlerParameters(mappedStatement.getConfiguration(), boundSql, params, commandType);
        }

        return invocation.proceed();
    }

    /**
     * 对一个list进行加密处理
     *
     * @param paramList
     * @throws Exception
     */
    private void listCrypt(List paramList) throws Exception {
        for (int i = 0; i < paramList.size(); i++) {
            Object val = paramList.get(i);
            if (CryptInterceptorUtil.isNotCrypt(val) || val instanceof Map) {
                break;
            }
            if (val instanceof String) {
                paramList.set(i, encrypt.encrypt(val.toString()));
            }
            entityEncrypt(val);
        }
    }


    /**
     * 对一个对象进行加密处理
     *
     * @param obj
     * @throws Exception
     */
    private void entityEncrypt(Object obj) throws Exception {
        CryptEntity declaredAnnotation = obj.getClass().getDeclaredAnnotation(CryptEntity.class);
        if (Objects.isNull(declaredAnnotation)) {
            return;
        }
        for (Field declaredField : obj.getClass().getDeclaredFields()) {
            CryptField annotation = declaredField.getAnnotation(CryptField.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            Object fieldVal = declaredField.get(obj);
            if (Objects.isNull(fieldVal)) {
                continue;
            }
            if (fieldVal instanceof CharSequence) {
                declaredField.set(obj, encrypt.encrypt(fieldVal.toString()));
            }
        }
    }


    private boolean isWriteCmd(SqlCommandType commandType) {
        return SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.INSERT.equals(commandType);

    }

    private void handlerParameters(Configuration configuration, BoundSql boundSql, Object params, SqlCommandType commandType) {
        MetaObject metaObject = configuration.newMetaObject(params);

        for (Field field : params.getClass().getDeclaredFields()) {
            CryptField declaredAnnotation = field.getDeclaredAnnotation(CryptField.class);
            if (Objects.isNull(declaredAnnotation)) {
                continue;
            }
            Object value = metaObject.getValue(field.getName());
            if (value instanceof CharSequence) {
                value = encryptValue(field, value);
                boundSql.setAdditionalParameter(field.getName(), value);
            }
        }
    }

    private Object encryptValue(Field field, Object value) {
        CryptField annotation = field.getAnnotation(CryptField.class);
        if (Objects.isNull(annotation)) {
            return value;
        }
        return encrypt.encrypt(value.toString());
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}