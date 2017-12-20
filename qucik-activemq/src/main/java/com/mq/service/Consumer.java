package com.mq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {  
        // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "mytest.queue2",concurrency = "3-10",id = "test")
    public void receiveQueue1(String text) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Consumer1收到的报文为:"+text);
    }
}