package com.quick.crypt.test;


import com.quick.db.crypt.annotation.EnableEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan(basePackages = "com.quick.crypt.test.dao")
@Slf4j
@EnableEncrypt(value = "123")
public class CryptApplication {

    @PostConstruct
    public void test() {
        log.info("after start !");
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptApplication.class);
    }

}