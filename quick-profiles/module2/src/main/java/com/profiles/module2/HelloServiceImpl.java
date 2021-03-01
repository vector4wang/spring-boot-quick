package com.profiles.module2;

import com.profiles.base.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    public void sayHello() {
        System.out.println("say module2 hello");
    }
}
