package com.dubbo.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
