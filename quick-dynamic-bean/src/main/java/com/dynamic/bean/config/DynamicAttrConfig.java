package com.dynamic.bean.config;

import com.dynamic.bean.box.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 动态属性 即动态生成的bean只是属性不同,可通过配置文件来设定需要动态启动的个数属性等等，按需配置
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Slf4j
@ConditionalOnProperty(value = "dynamic.attr.switch")
public class DynamicAttrConfig {

	@Value("${dynamic.attr.count}")
	private int count;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AbstractEnvironment environment;


	@PostConstruct
	public void dynamicCreate() {
		log.info("init create dynamic box");
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		for (int i = 1; i <= count; i++) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Person.class);
			/**
			 * 设置属性
			 */
			beanDefinitionBuilder.addPropertyValue("id", i);
			beanDefinitionBuilder.addPropertyValue("name", "wxc");
			beanDefinitionBuilder.addPropertyValue("address", "earth");
			beanDefinitionBuilder.addPropertyValue("age", 1000);
			beanDefinitionBuilder.addPropertyValue("birthday", new Date());

//			beanDefinitionBuilder.addDependsOn() 可以添加依赖如注入其他bean等
			/**
			 * 注册到spring容器中
			 */
			beanFactory.registerBeanDefinition("person_" + i, beanDefinitionBuilder.getBeanDefinition());
		}
	}
}
