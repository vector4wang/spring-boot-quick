package com.quick.mq.scenes.onesendmultirecver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 17:05
 * Description:
 */
@Component
@RabbitListener(queues = "helloQueue")
public class HelloMoreReceiver1 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1  : " + hello);
    }
}