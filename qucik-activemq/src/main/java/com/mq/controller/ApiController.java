package com.mq.controller;

import com.mq.service.Producer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@RestController
public class ApiController {

    @Resource
    private Producer producer;

    @RequestMapping("/send")
    public String sendMsg(){
        for (int i = 0; i < 10; i++) {
            try {
                producer.sendMessage("test_queue","11234567890");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }
}
