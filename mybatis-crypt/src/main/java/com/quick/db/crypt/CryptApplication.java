package com.quick.db.crypt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.quick.db.crypt.dao")
public class CryptApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptApplication.class);
    }
}