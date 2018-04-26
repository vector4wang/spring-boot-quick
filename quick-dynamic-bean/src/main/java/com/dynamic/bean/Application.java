package com.dynamic.bean;

import com.dynamic.bean.config.Abc;
import com.dynamic.bean.mq.Consumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import java.util.Arrays;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * ConditionalOnProperty 直接使用配置文件里的value，如果为true，则长长创建bean
     * @return
     */
    @Bean(name = "ActiveMqConsume")
    @ConditionalOnProperty(value = "spring.activemq.switch")
    public Consumer createConsumer() {
        System.out.println("成功创建bean");
        return new Consumer();
    }

    /**
     * 该Abc class位于类路径上时
     */
    @ConditionalOnClass(Abc.class)
    @Bean
    public String abc() {
        System.err.println("ConditionalOnClass true");
        return "";
    }

    @Bean
    public Abc createAbcBean() {
        return new Abc();
    }
//

    /**
     * 存在Abc类的实例时
     */
    @ConditionalOnBean(Abc.class)
    @Bean
    public String bean() {
        System.err.println("ConditionalOnBean is exist");
        return "";
    }

    @ConditionalOnMissingBean(Abc.class)
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
    @ConditionalOnProperty(
            value = {"spring.activemq.switch"},
            matchIfMissing = false)
    @Bean
    public String property() {
        System.err.println("property is true");
        return "";
    }

    /**
     * 打印容器里的所有bean name  (bean name 为方法名)
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
