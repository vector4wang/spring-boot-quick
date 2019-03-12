package com.dynamic.bean.config;

import com.dynamic.bean.box.AnonymousComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

/**
 * 动态注解，有一些bean是需要注解的，如"@JmsListener"
 * @author wangxc
 * @date: 2019/3/12 下午11:25
 *
 */
@ConditionalOnProperty(value = "dynamic.annotate.switch")
public class DynamicAnnotationConfig {

	private final static String SOURCE_NAME = "unpredictable_source";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AbstractEnvironment environment;

	@Value("${dynamic.annotate.unpredictable.value}")
	private String[] values;


	@Value("${dynamic.annotate.unpredictable.key")
	private String key;

	@Value("${dynamic.annotate.defaultVal")
	private String defaultVal;

	@PostConstruct
	public void init() {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

		if (values.length <= 0) {
			DynamicPropertySource dynamicPropertySource = DynamicPropertySource.builder().setSourceName(SOURCE_NAME).setProperty(key, defaultVal).build();
			environment.getPropertySources().addFirst(dynamicPropertySource);
			BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AnonymousComponent.class);
			beanFactory.registerBeanDefinition(defaultVal,definitionBuilder.getBeanDefinition());
		}

	}
}
