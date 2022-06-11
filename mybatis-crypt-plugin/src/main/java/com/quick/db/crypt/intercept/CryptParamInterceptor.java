package com.quick.db.crypt.intercept;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.util.CryptInterceptorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.*;

import static com.quick.db.crypt.util.CryptInterceptorUtil.ENTITY_FILED_ANN_MAP;

@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)
})
@Slf4j
public class CryptParamInterceptor extends CryptInterceptor implements Interceptor {

    /**
     * org.apache.ibatis.reflection.ParamNameResolver#getNamedParams(java.lang.Object[])
     * 跳过无用的paramx
     */
    private static final String GENERIC_NAME_PREFIX = "param";
    private static final String MAPPEDSTATEMENT = "mappedStatement";


    public CryptParamInterceptor(Encrypt encrypt) {
        super(encrypt);
        log.info("init CryptWriteInterceptor");
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(parameterHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPEDSTATEMENT);
        log.info("mappedStatement.getId(): {}", mappedStatement.getId());

        Object params = metaObject.getValue("parameterObject");
        if (Objects.isNull(params)) {
            return invocation.proceed();
        }

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
//                entityEncrypt(paramObj);
                entityEncrypt(paramValue);
            }
        } else if (params instanceof Map) {
            return invocation.proceed();
        } else {
            // 走到这里一般代表方法中只有一个参数，并且米有添加@param注解
            log.warn("请检查方法中参数的写法，是否有加@param！！！ 方法名为: {}", mappedStatement.getId());
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
     * 对一个加了CryptEntity的对象中加了CryptField的注解进行加密处理
     * 进入到这里要么是一个对象实体，要么是map类型的对象实体
     *
     * @param obj
     * @throws Exception
     */
    private void entityEncrypt(Object obj) throws Exception {
        log.info("obj.getClass().getName(): {}", obj.getClass().getName());
        String objFqn = obj.getClass().getName();
        List<String> objFieldList = ENTITY_FILED_ANN_MAP.getOrDefault(objFqn, new ArrayList<>());

        MetaObject metaObject = SystemMetaObject.forObject(obj);
        if (!CollectionUtils.isEmpty(objFieldList)) {
            // 不为空，已缓存
            objFieldList.forEach(s -> cryptField(s, metaObject));
        } else {
            // 为空，需要遍历 再缓存
            CryptEntity declaredAnnotation = obj.getClass().getDeclaredAnnotation(CryptEntity.class);
            if (Objects.isNull(declaredAnnotation)) {
                return;
            }
            Arrays.stream(obj.getClass().getDeclaredFields())
                    .filter(item -> Objects.nonNull(item.getAnnotation(CryptField.class)))
                    .forEach(item -> {
                        String fieldName = item.getName();
                        System.out.println(fieldName);
                        objFieldList.add(fieldName);
                        cryptField(fieldName, metaObject);
                    });
            ENTITY_FILED_ANN_MAP.put(objFqn, objFieldList);
        }

    }

    private void cryptField(String fieldName, MetaObject metaObject) {
        Object fieldVal = metaObject.getValue(fieldName);
        if (Objects.isNull(fieldVal)) {
            return;
        }
        if (fieldVal instanceof CharSequence) {
            metaObject.setValue(fieldName, encrypt.encrypt(fieldVal.toString()));
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof ParameterHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}