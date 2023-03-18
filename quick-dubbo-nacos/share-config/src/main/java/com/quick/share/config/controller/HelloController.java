package com.quick.share.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//自动刷新配置
public class HelloController {

	/**
	 * nacos 配置的json格式 {"user":"whhhh"}
	 */
	@Value(value = "${share.remark}")
	private String remark;

	@Value(value = "${test.haha}")
	private String redishost;
//
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@GetMapping("hello")
	public String hello() {
		Long test = redisTemplate.opsForValue().increment("test");
		return "hello" + remark + " redishost: " + redishost + ": " + test;
	}
}