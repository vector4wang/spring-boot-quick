package com.app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.app"})
//@EnableShiroConfig
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
