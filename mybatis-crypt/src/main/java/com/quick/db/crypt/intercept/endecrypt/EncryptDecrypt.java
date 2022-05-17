package com.quick.db.crypt.intercept.endecrypt;

import com.quick.db.crypt.intercept.DesUtil;
import com.quick.db.crypt.intercept.anno.CryptField;

import java.lang.reflect.Field;
import java.util.Objects;

public class  EncryptDecrypt {

    private final static String key = "vector4wang";
    /**
     * 加密
     *
     * @param declaredFields paramsObject所声明的字段
     * @param paramsObject   mapper中paramsType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    public static <T> T encrypt(Field[] declaredFields, T paramsObject) throws IllegalAccessException {
        for (Field field : declaredFields) {
            //取出所有被EncryptDecryptField注解的字段
            CryptField sensitiveField = field.getAnnotation(CryptField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                //暂时只实现String类型的加密
                if (object instanceof String) {
                    String value = (String) object;
                    //加密  Des加密工具
                    try {
                        field.set(paramsObject, DesUtil.encrypt(value,key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramsObject;
    }
    /**
     * 解密
     *
     * @param result resultType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    public static <T> T decrypt(T result) throws IllegalAccessException {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //取出所有被EncryptDecryptField注解的字段
            CryptField sensitiveField = field.getAnnotation(CryptField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一解密
                    try {
                        field.set(result, DesUtil.decrypt(value,key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}