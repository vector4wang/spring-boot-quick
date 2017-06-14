package com.quick.mq.recive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/14
 * Time: 19:39
 * Description:
 */
@Component
@RabbitListener(queues = "update")
public class UpdateReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("更新接受者 : " + msg);
    }
}
