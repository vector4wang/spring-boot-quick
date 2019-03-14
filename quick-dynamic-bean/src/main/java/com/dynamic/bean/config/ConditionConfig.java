package com.dynamic.bean.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 * @author wangxc
 * @date: 2019/3/12 下午11:24
 *
 */
@Component
public class ConditionConfig {

	/**
	 * 该Abc class位于类路径上时
	 */
	@ConditionalOnClass(DynamicAttrConfig.class)
	@Bean
	public String abc() {
		System.err.println("ConditionalOnClass true");
		return "";
	}

	@Bean
	public DynamicAttrConfig createAbcBean() {
		return new DynamicAttrConfig();
	}
	//

	/**
	 * 存在Abc类的实例时
	 */
	@ConditionalOnBean(DynamicAttrConfig.class)
	@Bean
	public String bean() {
		System.err.println("ConditionalOnBean is exist");
		return "";
	}

	@ConditionalOnMissingBean(DynamicAttrConfig.class)
	@Bean
	public String missBean() {
		System.err.println("ConditionalOnBean is missing");
		return "";
	}

	/**
	 * 表达式为true时
	 * spel  http://itmyhome.com/spring/expressions.html
	 */
	@ConditionalOnExpression(value = "2 > 1")
	@Bean
	public String expresssion() {
		System.err.println("expresssion is true");
		return "";
	}

	/**
	 * 配置文件属性是否为true
	 */
	@ConditionalOnProperty(value = {"spring.activemq.switch"}, matchIfMissing = false)
	@Bean
	public String property() {
		System.err.println("property is true");
		return "";
	}

	/**
	 * 打印容器里的所有bean name  (box name 为方法名)
	 * @param appContext
	 * @return
	 */
	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {
			String[] beans = appContext.getBeanDefinitionNames();
			Arrays.stream(beans).sorted().forEach(System.out::println);
		};
	}
}
