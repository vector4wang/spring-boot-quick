package com.dynamic.bean.config;

import com.dynamic.bean.box.AnonymousComponent;
import com.dynamic.bean.box.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2019/3/13 下午11:38
 *
 */
@Component
@Slf4j
public class BeanConfig {

	private final static String SOURCE_NAME = "SOURCE_NAME";

	@Value("${dynamic.attr.count}")
	private int count;

	@Value("${dynamic.annotate.unpredictable.value}")
	private String[] values;


	@Value("${dynamic.annotate.unpredictable.key}")
	private String key;

	@Value("${dynamic.annotate.defaultVal")
	private String defaultVal;


	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AbstractEnvironment environment;


	@Bean
	@ConditionalOnProperty(prefix = "dynamic.", value = "attr.switch")
	public void createDynamicAttrConfig() {
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

	@Bean
	@ConditionalOnProperty(prefix = "dynamic.", value = "annotate.switch")
	public void createDynamicAnnotationConfig() {
		for (int i = 0; i < values.length; i++) {
			DynamicPropertySource dynamicPropertySource = DynamicPropertySource.builder().setSourceName(SOURCE_NAME)
					.setProperty(key, values[i]).build();
			environment.getPropertySources().addFirst(dynamicPropertySource);
			log.info("{}--->{}", key, environment.getProperty(key));
		}
	}
}
