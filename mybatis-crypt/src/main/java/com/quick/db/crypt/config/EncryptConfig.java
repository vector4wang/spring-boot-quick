package com.quick.db.crypt.config;


import com.quick.db.crypt.annotation.EnableEncrypt;
import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.security.NoSuchAlgorithmException;

/**
 * 增加默认配置，通过@EnableEncrypt引入
 */
@Slf4j
public class EncryptConfig implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {


        String value = (String) annotationMetadata.getAnnotationAttributes(EnableEncrypt.class.getName()).get("value");
        log.info("enable encrypt value: {}", value);
        Encrypt encrypt = null;
        try {
            encrypt = new AesDesDefaultEncrypt(value);
        } catch (NoSuchAlgorithmException e) {
            log.error("init Encrypt error", e);
        }


        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(EnCryptConfigurationCustomizer.class);
        bdb.addConstructorArgValue(encrypt);
        registry.registerBeanDefinition("configurationCustomizer", bdb.getBeanDefinition());

    }

////    @Bean
//    Encrypt encryptor() throws Exception {
//        return new AesDesDefaultEncrypt("1870577f29b17d6787782f35998c4a79");
//    }


//    @Bean
//    public ConfigurationCustomizer configurationCustomizer(Encrypt encrypt) throws Exception {
//        log.info("configurationCustomizer encrypt: {}", encrypt);
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(Configuration configuration) {
//                configuration.addInterceptor(new CryptReadInterceptor(encrypt));
//                configuration.addInterceptor(new CryptWriteInterceptor(encrypt));
//            }
//        };
//    }
}
