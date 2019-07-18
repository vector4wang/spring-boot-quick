package com.quick.kafka.controller;

import com.quick.kafka.service.ProducerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author vector
 * @date: 2019/7/18 0018 11:15
 */
@RestController
@RequestMapping("/kafka")
public class ApiController {

    @Resource
    private ProducerService producerService;

    @PostMapping("/send/{topic}")
    public String send(@PathVariable("topic") String topic, @RequestBody String body) {
        producerService.sendMessage(topic, body);
        return "send success";
    }
}
