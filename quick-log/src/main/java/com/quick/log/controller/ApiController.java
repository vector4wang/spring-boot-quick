package com.quick.log.controller;

import com.quick.log.service.LoggerService;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping("s")
	public String ss() {
		return "123";
	}

    @GetMapping("/exception")
    public String exce() {
        System.out.println("异常");
        throw new IllegalArgumentException("异常了");
    }


}
