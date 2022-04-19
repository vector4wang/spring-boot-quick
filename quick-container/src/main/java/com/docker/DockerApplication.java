package com.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


	@Value("${test.value:}")
	private String conVal;

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/hello")
	public String hello() {
		log.info("接收到请求》》》》》》》》》》》》》》》》》》》》》》》"+UUID.randomUUID().toString());
		Object docker = "redis server error";
		try {
			docker = redisTemplate.opsForValue().getAndSet("docker", UUID.randomUUID().toString());
		} catch (Exception e) {
			log.error("redis",e);
		}
		return "<h1>Hello Spring-Boot Maven K3S</h1><br/>" + docker +"</br>" + conVal;
	}

	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class);
	}
}
