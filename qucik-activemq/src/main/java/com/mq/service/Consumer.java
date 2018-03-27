package com.mq.service;

import com.alibaba.fastjson.JSON;
import com.mq.model.TalentMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//    @JmsListener(destination = "${jsa.activemq.queue.name}",concurrency = "2-6")
    public void receiveQueue1(String message) throws InterruptedException {
        System.out.println("Consumer1收到的报文为: "+ message);
    }

}