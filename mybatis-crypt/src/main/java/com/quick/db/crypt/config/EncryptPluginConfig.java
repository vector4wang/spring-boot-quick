package com.quick.db.crypt.config;

import com.quick.db.crypt.encrypt.AesDesEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.intercept.CryptReadInterceptor;
import com.quick.db.crypt.intercept.CryptWriteInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EncryptPluginConfig {


//    @Bean
//    Encrypt encryptor() throws Exception {
//        return new AesDesEncrypt("1870577f29b17d6787782f35998c4a79");
//    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() throws Exception {
        CryptReadInterceptor cryptReadInterceptor = new CryptReadInterceptor(null);
        CryptWriteInterceptor cryptWriteInterceptor = new CryptWriteInterceptor(null);
        return (configuration) -> {
            configuration.addInterceptor(cryptReadInterceptor);
            configuration.addInterceptor(cryptWriteInterceptor);
        };
    }
}