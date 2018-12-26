package com.quick.jwt.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select()//
				.apis(RequestHandlerSelectors.any())//
				.paths(Predicates.not(PathSelectors.regex("/error")))//
				.build()//
				.apiInfo(metadata())//
				.useDefaultResponseMessages(false)//
				.securitySchemes(
						new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token", "Authorization", "Header"))))//
				.tags(new Tag("users", "Operations about users"))//
				.tags(new Tag("ping", "Just a ping"))//
				.genericModelSubstitutes(Optional.class);

	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()//
				.title("JSON Web Token Authentication API")//
				.description(
						"This is a sample JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/). For this sample, you can use the `admin` or `client` users (password: admin and client respectively) to test the authorization filters. Once you have successfully logged in and obtained the token, you should click on the right top button `Authorize` and introduce it with the prefix \"Bearer \".")//
				.version("1.0.0")//
				.license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")//
				.contact(new Contact(null, null, "vector4wang@qq.com"))//
				.build();
	}

}