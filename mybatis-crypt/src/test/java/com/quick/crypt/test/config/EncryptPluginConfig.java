package com.quick.crypt.test.config;

import com.quick.db.crypt.intercept.CryptReadInterceptor;
import com.quick.db.crypt.intercept.CryptWriteInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;


@Configuration
@Slf4j
public class EncryptPluginConfig {


//    @Bean
//    Encrypt encryptor() throws Exception {
//        return new AesDesEncrypt("1870577f29b17d6787782f35998c4a79");
//    }

//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() throws Exception {
//        log.info("ConfigurationCustomizer init");
//        CryptReadInterceptor cryptReadInterceptor = new CryptReadInterceptor();
//        CryptWriteInterceptor cryptWriteInterceptor = new CryptWriteInterceptor();
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                configuration.addInterceptor(cryptReadInterceptor);
//                configuration.addInterceptor(cryptWriteInterceptor);
//            }
//        };
//    }

//    @Bean
//    public void initCryptorInterceptor(SqlSessionFactory sqlSessionFactory) {
//        CryptReadInterceptor readInterceptor = new CryptReadInterceptor();
//        CryptWriteInterceptor cryptWriteInterceptor = new CryptWriteInterceptor();
//        sqlSessionFactory.getConfiguration().addInterceptor(readInterceptor);
//        sqlSessionFactory.getConfiguration().addInterceptor(cryptWriteInterceptor);
//    }

    @Bean
    public CryptReadInterceptor cryptReadInterceptor() {
        CryptReadInterceptor cryptReadInterceptor = new CryptReadInterceptor();
        return cryptReadInterceptor;
    }

    @Bean
    public CryptWriteInterceptor cryptWriteInterceptor() {
        CryptWriteInterceptor cryptWriteInterceptor = new CryptWriteInterceptor();
        return cryptWriteInterceptor;
    }

}