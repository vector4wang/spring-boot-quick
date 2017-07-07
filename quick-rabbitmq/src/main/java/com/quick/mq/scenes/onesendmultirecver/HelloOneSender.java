package com.quick.mq.scenes.onesendmultirecver;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 17:04
 * Description:单生产者-多消费者
 */
@Component
public class HelloOneSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }
}
