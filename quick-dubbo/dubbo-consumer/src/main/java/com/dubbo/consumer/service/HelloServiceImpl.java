package com.dubbo.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloServiceImpl implements HelloService {

    @Reference
    public HelloService helloService;

    @Override
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }
}
