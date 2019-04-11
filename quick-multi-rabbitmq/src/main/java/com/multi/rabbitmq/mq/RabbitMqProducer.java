package com.multi.rabbitmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author vector
 * @date: 2019/4/11 0011 11:20
 */
@Component
@Slf4j
public class RabbitMqProducer {

    @Resource(name = "firstRabbitTemplate")
    private RabbitTemplate firstRabbitTemplate;

    @Resource(name = "secondRabbitTemplate")
    private RabbitTemplate secondRabbitTemplate;

    public void firstSend() {
        String msg = "hello1 " + new Date();
        System.out.println("Sender : " + msg);
        this.firstRabbitTemplate.convertAndSend("tbd_resume_id_dev_5", msg);
    }

    public void secondSend() {
        String msg = "hello2 " + new Date();
        System.out.println("Sender : " + msg);
        this.secondRabbitTemplate.convertAndSend("tbd_resume_id_rc_5", msg);
    }

}
