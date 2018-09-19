package com.quick.feign;

import com.alibaba.fastjson.JSON;
import com.quick.feign.entity.BaseResp;
import com.quick.feign.entity.DomainDetail;
import com.quick.feign.service.FeignService;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	FeignService feignTest(){


		return HystrixFeign.builder()
				.client(new OkHttpClient())
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.logger(new Slf4jLogger(FeignService.class))
				.logLevel(Logger.Level.FULL)
				.options(new Request.Options(2000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))

				.target(FeignService.class, "https://www.sojson.com", new FeignService() {
					@Override
					public BaseResp<DomainDetail> getDomain(String domain) {
						BaseResp baseResp = JSON.parseObject(
								"{\"data\":{\"id\":\"11000002000016\",\"sitename\":\"新浪网\",\"sitedomain\":\"sina.com.cn\",\"sitetype\":\"交互式\",\"cdate\":\"2016-01-21\",\"comtype\":\"企业单位\",\"comname\":\"北京新浪互联信息服务有限公司\",\"comaddress\":\"北京市网安总队\",\"updateTime\":\"2017-09-09\"},\"status\":200}",
								BaseResp.class);
						return baseResp;
					}
				});
	}

}
