package com.profiles;

import com.profiles.base.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TestMain {

    @Autowired
    private HelloService helloService;


    @PostConstruct
    public void init() {
        helloService.sayHello();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class);
    }
}
