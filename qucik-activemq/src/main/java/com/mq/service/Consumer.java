package com.mq.service;

import com.alibaba.fastjson.JSON;
import com.mq.model.TalentMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {  
        // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "${jsa.activemq.queue.name1}",concurrency = "2-6")
    public void receiveQueue1(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Consumer1收到的报文为: "+ JSON.parseObject(message, TalentMQMessage.class));
    }

    @JmsListener(destination = "${jsa.activemq.queue.name2}",concurrency = "2-6")
    public void receiveQueue2(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Consumer1收到的报文为: "+ JSON.parseObject(message, TalentMQMessage.class));
    }

    @JmsListener(destination = "${jsa.activemq.queue.name3}",concurrency = "2-6")
    public void receiveQueue3(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Consumer1收到的报文为: "+ JSON.parseObject(message, TalentMQMessage.class));
    }
}