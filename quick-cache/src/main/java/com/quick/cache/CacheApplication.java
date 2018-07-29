package com.quick.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}
}
