package com.vue.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/2/11 0011
 * Time: 17:20
 * Description:
 */
@RestController
public class ApiController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Quick SpringBoot-Vue";
    }
}
