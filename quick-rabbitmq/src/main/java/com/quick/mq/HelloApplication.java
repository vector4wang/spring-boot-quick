package com.quick.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://www.cnblogs.com/boshen-hzb/p/6841982.html
 */
@SpringBootApplication
@EnableSwagger2
public class HelloApplication {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.quick.mq.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBootRabbitMQ")
                .description("api接口的文档整理，支持在线测试")
                .termsOfServiceUrl("http://blog.wangxc.club/")
                .contact("Vector.Wang")
                .version("1.0")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }
}