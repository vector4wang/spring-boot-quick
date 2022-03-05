package com.quick.nacos;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:28 PM
 *
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"com.quick.nacos.service"})
public class NacosServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(NacosServiceApp.class, args);
	}
}
