package com.multi.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vector
 * @Data 2018/8/10 0010
 * @Description TODO
 */
@SpringBootApplication
@MapperScan("com.modules.dao")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
