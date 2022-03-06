package com.quick.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:28 PM
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(NacosServiceApp.class, args);
	}
}
