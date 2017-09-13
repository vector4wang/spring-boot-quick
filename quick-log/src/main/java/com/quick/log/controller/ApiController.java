package com.quick.log.controller;

import com.quick.log.service.LoggerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/log")
public class ApiController {

    @Resource
    private LoggerService loggerService;


    @RequestMapping("/show")
    public Map<String,Object> shotLog(@RequestParam("name")String name,@RequestParam("age") int age){
        Map<String,Object> result = new HashMap<>();
        result.put("name",name);
        result.put("age",age);
        for(int i=0;i<100000000;i++) {

            loggerService.showLog();
        }
        return result;
    }

    @RequestMapping("/show2")
    public Map<String,Object> shotLog(@RequestBody String parms){
        Map<String,Object> result = new HashMap<>();
//        result.put("name",name);
//        result.put("age",age);
//        System.out.println(parms);
        loggerService.showLog();
        return result;
    }


}
