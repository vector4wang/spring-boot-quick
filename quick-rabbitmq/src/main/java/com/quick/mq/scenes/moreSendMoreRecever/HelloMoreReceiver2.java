package com.quick.mq.scenes.moreSendMoreRecever;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 18:10
 * Description:
 */
@Component(value = "msmr2")
public class HelloMoreReceiver2 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver2  : " + hello);
    }

}
