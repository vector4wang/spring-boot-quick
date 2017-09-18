package com.quick.mq.scenes.moreSendMoreRecever;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 18:07
 * Description:
 */
@Component(value = "msmr1")
public class HelloMoreReceiver1 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1  : " + hello);
    }
}
