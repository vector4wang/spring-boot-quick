package com.shiro.quick.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnoController {

    @GetMapping("/anno/hello1")
    public String hello() {
        return "anno hello";
    }
}
