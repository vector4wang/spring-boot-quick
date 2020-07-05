package com.disconfig;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 配置VM参数-Denv=DEV
 * @author wangxc
 * @date: 2019/12/12 下午9:59
 *
 */
@SpringBootApplication
@EnableApolloConfig
public class DisconfigApplication implements CommandLineRunner {

	@Value("${msg}")
	private String msg;

	@Value("${redis.port:100}")
	private int redisPort;


	public static void main(String[] args) {
		SpringApplication.run(DisconfigApplication.class);

	}

	@Override
	public void run(String... args) throws Exception {
		Executors.newSingleThreadExecutor().submit(() -> {
			while (true) {
				System.out.println("启动完成：" + msg);
				System.out.println("redisPort: " + redisPort);
				TimeUnit.SECONDS.sleep(1);
			}
		});

	}
}
