package com.modules;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/1
 * Time: 8:42
 */
@SpringBootApplication
@MapperScan("com.modules.dao")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
