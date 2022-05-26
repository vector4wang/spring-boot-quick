package com.quick.db.crypt.config;


import com.quick.db.crypt.annotation.EnableEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.intercept.CryptParamInterceptor;
import com.quick.db.crypt.intercept.CryptReadInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * 增加默认配置，通过@EnableEncrypt引入
 */
@Slf4j
public class EncryptConfig implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableEncrypt.class.getName()));
        String value = annoAttrs.getString("value");
        Class<? extends Encrypt> encryptIml = annoAttrs.getClass("encryptIml");

        log.info("enable encrypt value: {}", value);
        BeanDefinitionBuilder bdb0 = BeanDefinitionBuilder.rootBeanDefinition(encryptIml);
        if (!StringUtils.isEmpty(value)) {
            bdb0.addConstructorArgValue(value);
        }

        /**
         * 方便后续系统引入
         */
        registry.registerBeanDefinition("encrypt", bdb0.getBeanDefinition());

        BeanDefinitionBuilder bdb1 = BeanDefinitionBuilder.rootBeanDefinition(CryptReadInterceptor.class);
        bdb1.addConstructorArgReference("encrypt");
        registry.registerBeanDefinition("cryptReadInterceptor", bdb1.getBeanDefinition());

        BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(CryptParamInterceptor.class);
        bdb2.addConstructorArgReference("encrypt");
        registry.registerBeanDefinition("cryptParamInterceptor", bdb2.getBeanDefinition());

    }
}
