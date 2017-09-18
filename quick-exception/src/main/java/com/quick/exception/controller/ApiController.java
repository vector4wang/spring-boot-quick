package com.quick.exception.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/hello")
    public Map<String,String> hello(@RequestParam("str") String str){

        Map<String,String> map = new HashMap<String, String>();
        map.put("result",str);
        return map;
    }
}
