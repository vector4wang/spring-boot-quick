package com.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/1/2
 * Time: 16:08
 * Description:
 */
@Component
public class SendService {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void send() {
        System.out.println("发送");
        this.rabbitTemplate.convertAndSend("hello.test", "测试");
        System.out.println("发送陈宫");
    }
}
