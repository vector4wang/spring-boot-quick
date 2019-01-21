package com.mq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Consumer {

    @JmsListener(destination = "${jsa.activemq.queue.name}",concurrency = "2-6")
    public void receiveQueue1(TextMessage message) throws InterruptedException {
        System.out.println("Consumer1收到的报文为: "+ message);
    }

}