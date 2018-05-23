package com.dubbo.consumer.controller;

import com.dubbo.api.HelloService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ApiController {

    @Resource
    public HelloService helloService;

    @RequestMapping("/sayHello/{name}")
    public String hello(@PathVariable("name") String name) {
        return helloService.sayHello(name);
    }

}
