package com.quick.share.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author wangxc
 * @date: 2022/3/21 10:21 PM
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ShareConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShareConfigApplication.class, args);
	}
}
