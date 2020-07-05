package com.shiro.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 *
 * @author wangxc
 * @date: 2019-12-11 22:22
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		//序列化配置
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(
				QuoteFieldNames,// 输出key时是否使用双引号
				WriteMapNullValue,// 是否输出值为null的字段
				WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
				WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
				WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null
				//WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
				//WriteNullStringAsEmpty,// null String不输出
				//WriteMapNullValue,//null String也要输出
				//WriteDateUseDateFormat,//Date的日期转换器
				DisableCircularReferenceDetect//禁止循环引用
		);
		converter.setFastJsonConfig(config);
		converters.add(converter);
	}
}
