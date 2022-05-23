package com.quick.db.crypt.intercept;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.quick.db.crypt.util.CryptInterceptorUtil.ENTITY_FILED_ANN_MAP;

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {java.sql.Statement.class})
})
@Slf4j
public class CryptReadInterceptor extends CryptInterceptor implements Interceptor {

    private static final String MAPPED_STATEMENT = "mappedStatement";

//    public CryptReadInterceptor() {
//        super();
//    }

    public CryptReadInterceptor(Encrypt encrypt) {
        super(encrypt);
        log.info("init CryptReadInterceptor");
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
        if (cryptEntity == null || resultMap == null) {
            return results;
        }

        List<String> cryptFieldList = getCryptField(resultMap);

        cryptFieldList.forEach(item -> {
            results.forEach(x -> {
                MetaObject objMetaObject = SystemMetaObject.forObject(x);
                Object value = objMetaObject.getValue(item);
                if (Objects.nonNull(value)) {
                    objMetaObject.setValue(item, encrypt.decrypt(value.toString()));
                }
            });
        });

        return results;
    }

    /**
     * 获取需要加密的属性
     *
     * @param resultMap
     * @return
     */
    private List<String> getCryptField(ResultMap resultMap) {
        Class<?> clazz = resultMap.getType();
        log.info("clazz: {}", clazz);
        List<String> fieldList = ENTITY_FILED_ANN_MAP.getOrDefault(clazz.getName(), new ArrayList<>());
        if (CollectionUtils.isEmpty(fieldList)) {
            for (Field declaredField : clazz.getDeclaredFields()) {
                CryptField cryptField = declaredField.getAnnotation(CryptField.class);
                if (cryptField != null) {
                    fieldList.add(declaredField.getName());
                }
            }
        }
        return fieldList;
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}