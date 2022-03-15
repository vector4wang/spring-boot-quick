package com.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.UID;
import java.util.UUID;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/1/17
 * Time: 13:49
 * Description:
 */
@SpringBootApplication
@RestController
@Slf4j
public class DockerApplication {

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/hello")
	public String hello() {
		log.info("接收到请求》》》》》》》》》》》》》》》》》》》》》》》"+UUID.randomUUID().toString());
		Object docker = redisTemplate.opsForValue().getAndSet("docker", UUID.randomUUID().toString());
		return "<h1>Hello Spring-Boot Maven Docker</h1>" + docker;
	}

	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class);
	}
}
