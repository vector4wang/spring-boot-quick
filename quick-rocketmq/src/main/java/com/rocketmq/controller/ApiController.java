package com.rocketmq.controller;

import com.rocketmq.service.MQService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:43
 * Description:
 */
@RestController
public class ApiController {

    @Resource
    private MQService mqService;

    @RequestMapping("/send")
    public String sendMsg(@RequestParam("msg") String msg) {
        mqService.sendMsg(msg);
        return "hello world";
    }
}
