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
    private static final String GENERIC_NAME_PREFIX = "param";
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
         * 只有insert和update的才做处理
         */
        if (!isWriteCmd(commandType)) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue(BOUND_SQL);
        Object params = boundSql.getParameterObject();
        /**
         * params 单个参数 insert(user)     Object
         * params 集合参数 insert(list<user>)  MapperMethod$ParamMap
         * https://github.com/miaoxinwei/mybatis-crypt/blob/master/src/main/java/org/apache/ibatis/plugin/CryptInterceptor.java
         */
        log.info("params.getClass().getTypeName(): {}", params.getClass().getTypeName());

        /**
         * MapperMethod.ParamMap
         * 参数使用@param，程序会自动增加对应的paramx
         */
        if (params instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) params;
            for (Map.Entry<String, Object> paramObj : paramMap.entrySet()) {
                Object paramValue = paramObj.getValue();
                if (CryptInterceptorUtil.isNotCrypt(paramValue) || paramObj.getKey().contains(GENERIC_NAME_PREFIX)) {
                    continue;
                }
                log.info("paramValue.getClass().getTypeName(): {}", paramValue.getClass().getTypeName());
                // 集合类型的参数
                if (paramValue instanceof Collection) {
                    listEntityCrypt((Collection) paramValue);
                    continue;
                }
                // 对象类型的参数
                entityEncrypt(paramObj);
            }
        } else if (params instanceof Map) {
            return invocation.proceed();
        } else {
            // 走到这里一般代表方法中只有一个参数，并且米有添加@param注解
            entityEncrypt(params);
        }
        return invocation.proceed();
    }


    /**
     * 对一个list进行加密处理
     * 暂时不考虑List<List> 这种情况
     *
     * @param paramList
     * @throws Exception
     */
    private void listEntityCrypt(Collection paramList) throws Exception {
        Iterator iterator = paramList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (CryptInterceptorUtil.isNotCrypt(next)) {
                break;
            }
            entityEncrypt(next);
        }
    }


    /**
     * 对一个加了CryptEntity的对象中加了CryptFeild的注解进行加密处理
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
            MetaObject metaObject = SystemMetaObject.forObject(obj);
            Object fieldVal = metaObject.getValue(declaredField.getName());
            if (Objects.isNull(fieldVal)) {
                continue;
            }
            if (fieldVal instanceof CharSequence) {
                metaObject.setValue(declaredField.getName(), encrypt.encrypt(fieldVal.toString()));
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