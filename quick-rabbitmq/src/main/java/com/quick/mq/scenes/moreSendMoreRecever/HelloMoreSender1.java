package com.quick.mq.scenes.moreSendMoreRecever;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 17:52
 * Description:
 */
@Component
public class HelloMoreSender1 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }

}
