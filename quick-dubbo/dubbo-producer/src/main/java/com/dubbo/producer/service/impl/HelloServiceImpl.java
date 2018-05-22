package com.dubbo.producer.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.producer.service.HelloService;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
