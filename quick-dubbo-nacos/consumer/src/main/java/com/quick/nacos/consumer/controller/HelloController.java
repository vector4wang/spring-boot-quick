package com.quick.nacos.consumer.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.quick.nacos.provider.HelloProvider;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:40 PM
 *
 */
@RestController
@RefreshScope//自动刷新配置
public class HelloController {

	@DubboReference(interfaceClass = HelloProvider.class,
			interfaceName = "${service.name}",
			version = "${service.version}")
	private HelloProvider helloProvider;
	/**
	 * nacos 配置的json格式 {"user":"whhhh"}
	 */
	@Value(value = "${user}")
	private String helloSuffix;

	@GetMapping("hello/{param}")
	public String hello(@PathVariable("param") String param) {
		return helloProvider.hello(param + helloSuffix);
	}
}
