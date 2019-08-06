package com.quick.starter.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author vector
 * @date: 2019/8/6 0006 16:53
 */
@Configuration
@ConditionalOnClass(HelloService.class)
@EnableConfigurationProperties(QuickProperties.class)
public class QuickAutoConfiguration {

    @Autowired
    private QuickProperties quickProperties;


    @Bean
    @ConditionalOnMissingBean(HelloService.class)
    public HelloService helloService(){
        System.out.println("Execute Create New Bean");
        HelloService helloService = new HelloService();
        helloService.setMsg(quickProperties.getName());
        return helloService;
    }

}
