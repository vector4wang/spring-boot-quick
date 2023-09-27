package com.quick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quick.mapper")
public class CustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomApplication.class);
    }
}