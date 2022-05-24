//package com.quick.db.crypt.config;
//
//import com.quick.db.crypt.encrypt.Encrypt;
//import com.quick.db.crypt.intercept.CryptReadInterceptor;
//import com.quick.db.crypt.intercept.CryptWriteInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.Configuration;
//import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
//
//@Slf4j
//public class EnCryptConfigurationCustomizer implements ConfigurationCustomizer {
//
//    private Encrypt encrypt;
//
//    public EnCryptConfigurationCustomizer(Encrypt encrypt) {
//        log.info("EnCryptConfigurationCustomizer init");
//        this.encrypt = encrypt;
//    }
//
//    @Override
//    public void customize(Configuration configuration) {
//        log.info("EnCryptConfigurationCustomizer.customize");
//        configuration.addInterceptor(new CryptReadInterceptor(encrypt));
//        configuration.addInterceptor(new CryptWriteInterceptor(encrypt));
//    }
//}
