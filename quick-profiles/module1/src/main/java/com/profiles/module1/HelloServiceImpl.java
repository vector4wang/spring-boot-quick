package com.profiles.module1;

import com.profiles.base.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    public void sayHello() {
        System.out.println("say module1 hello");
    }
}
