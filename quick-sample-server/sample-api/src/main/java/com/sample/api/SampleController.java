package com.sample.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class SampleController {

    @RequestMapping
    public String hello() {
        return "hello";
    }
}
