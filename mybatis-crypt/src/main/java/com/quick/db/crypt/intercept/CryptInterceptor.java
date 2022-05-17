package com.quick.db.crypt.intercept;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * https://github.com/miaoxinwei/mybatis-crypt/blob/master/src/main/java/org/apache/ibatis/plugin/CryptInterceptor.java
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class CryptInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取参数
        Object[] args = invocation.getArgs();

        // 入参
        Object parameter = args[1];
        MappedStatement statement = (MappedStatement) args[0];

        if (!isNotCrypt(parameter)) {

        }


        SqlCommandType sqlCommandType = null;

        for (Object object : args) {
            // 从MappedStatement参数中获取到操作类型

        }


        return null;
    }

    private boolean isNotCrypt(Object parameter) {
        return parameter == null
                || parameter instanceof Double
                || parameter instanceof Integer
                || parameter instanceof Long
                || parameter instanceof Boolean;
    }


    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}