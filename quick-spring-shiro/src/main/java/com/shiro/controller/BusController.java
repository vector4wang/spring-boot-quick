package com.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/biz")
@RestController
@Slf4j
public class BusController {

    @GetMapping("/info")
    public String info() {
        return "auth success info";
    }
}
