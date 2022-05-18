package com.quick.db.crypt.intercept;

import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.PluginUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Objects;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class CryptWriteInterceptor implements Interceptor {

    private static final String MAPPEDSTATEMENT="delegate.mappedStatement";
    private static final String BOUND_SQL="delegate.boundSql";

    private Encrypt encrypt;

    public CryptWriteInterceptor(Encrypt encrypt) {
        Objects.requireNonNull(encrypt,"encrypt should not be null!");
        this.encrypt = encrypt;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue(MAPPEDSTATEMENT);
        SqlCommandType commandType = mappedStatement.getSqlCommandType();


        return null;
    }

    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}