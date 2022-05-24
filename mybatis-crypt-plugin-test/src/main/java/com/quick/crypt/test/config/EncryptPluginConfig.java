//package com.quick.crypt.test.config;
//
//import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;
//import com.quick.db.crypt.intercept.CryptReadInterceptor;
//import com.quick.db.crypt.intercept.CryptWriteInterceptor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.security.NoSuchAlgorithmException;
//
//
////@Configuration
//@Slf4j
//public class EncryptPluginConfig {
//
//
//    @Bean(name = "cryptReadInterceptor")
//    public CryptReadInterceptor cryptReadInterceptor() throws NoSuchAlgorithmException {
//        return new CryptReadInterceptor(new AesDesDefaultEncrypt());
//    }
//
//    @Bean(name = "cryptWriteInterceptor")
//    public CryptWriteInterceptor cryptWriteInterceptor() throws NoSuchAlgorithmException {
//        return new CryptWriteInterceptor(new AesDesDefaultEncrypt());
//    }
//
//}