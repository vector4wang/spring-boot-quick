package com.quick.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 *
 * @author wangxc
 * @date: 2020/2/18 下午3:03
 *
 */
@Configuration
public class ResourceBundleConfig {
	@Bean
	public ResourceBundleMessageSource messageSource() {
		Locale.setDefault(Locale.CHINESE);
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		/**
		 * 也可以在application.properties中添加配置
		 * spring.messages.basename=i18n/messages
		 * spring.messages.encoding=UTF-8
		 */
		source.setBasenames("i18n/messages");// name of the resource bundle
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	/**
	 * springboot 中切换语言，需要使用下面的bean，注意bean的name
	 * 一定要是localeResolver
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver(){
		final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		//final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("zh", "CN"));
		return localeResolver;
	}
}
