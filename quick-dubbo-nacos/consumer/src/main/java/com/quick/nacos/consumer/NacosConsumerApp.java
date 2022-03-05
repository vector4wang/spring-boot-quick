package com.quick.nacos.consumer;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:39 PM
 *
 */
@SpringBootApplication
@EnableDubbo
@NacosPropertySource(dataId = "nacos-consumer",autoRefreshed = true)
@EnableNacosConfig
public class NacosConsumerApp {
	public static void main(String[] args) {
		SpringApplication.run(NacosConsumerApp.class, args);
	}
}
