package com.quick.db.crypt.intercept;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import com.quick.db.crypt.encrypt.AesDesEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.PluginUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class CryptWriteInterceptor implements Interceptor {

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
        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        BoundSql boundSql = (BoundSql) metaObject.getValue(BOUND_SQL);
        Object params = boundSql.getParameterObject();
        if (params instanceof Map) {
            return invocation.proceed();
        }

        if (!isWriteCmd(commandType)) {
            return invocation.proceed();
        }

        CryptEntity cryptEntity = params != null ? params.getClass().getAnnotation(CryptEntity.class) : null;
        if (cryptEntity != null) {
            handlerParameters(mappedStatement.getConfiguration(), boundSql, params, commandType);
        }

        return invocation.proceed();
    }

    private boolean isWriteCmd(SqlCommandType commandType) {
        return SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.INSERT.equals(commandType);

    }

    private void handlerParameters(Configuration configuration, BoundSql boundSql, Object params, SqlCommandType commandType) {
        HashMap<String, Object> newValues = new HashMap<>(16);
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