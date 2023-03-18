package com.quick.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:39 PM
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApp {
	public static void main(String[] args) {
		SpringApplication.run(NacosConsumerApp.class, args);
	}
}
