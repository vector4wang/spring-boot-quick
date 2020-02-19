package com.quick.source.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 *
 * @author wangxc
 * @date: 2020/2/18 下午3:05
 *
 */
@RestController
public class ApiController {

	@Autowired
	private MessageSource messageSource;

	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) {

		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = new Locale("zh","CN");
		switch (lang) {
			case "zh_CN":
				System.out.println("切换到中文");
				break;
			case "en_US":
				System.out.println("change language to English");
				locale = new Locale("en","US");
				break;
			default:
				System.out.println("设置默认语言为中文");
				// 默认设置中文
		}
		localeResolver.setLocale(request,response,locale);

		String message = messageSource.getMessage("success", null, locale);
		return message;
	}
}
