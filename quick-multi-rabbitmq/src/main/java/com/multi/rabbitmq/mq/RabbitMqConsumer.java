package com.multi.rabbitmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author vector
 * @date: 2019/4/11 0011 10:59
 */
@Slf4j
public class RabbitMqConsumer {

    @RabbitListener(queues = {"${rabbit.mq.queue0}"}, containerFactory = "jmsQueue4TalentIdContainer0")
    public void firstMq0(String msg) {
        log.info("dev0 receive msg: {}", msg);
    }

    @RabbitListener(queues = {"${rabbit.mq.queue1}"}, containerFactory = "jmsQueue4TalentIdContainer1")
    public void firstMq1(String msg) {
        log.info("dev1 receive msg: {}", msg);
    }

    @RabbitListener(queues = {"${rabbit.mq.queue2}"}, containerFactory = "jmsQueue4TalentIdContainer2")
    public void firstMq2(String msg) {
        log.info("dev2 receive msg: {}", msg);
    }

    @RabbitListener(queues = {"${rabbit.mq.queue3}"}, containerFactory = "jmsQueue4TalentIdContainer3")
    public void firstMq3(String msg) {
        log.info("dev3 receive msg: {}", msg);
    }

    @RabbitListener(queues = {"${rabbit.mq.queue4}"}, containerFactory = "jmsQueue4TalentIdContainer4")
    public void firstMq4(String msg) {
        log.info("dev4 receive msg: {}", msg);
    }

    @RabbitListener(queues = {"${rabbit.mq.queue5}"}, containerFactory = "jmsQueue4TalentIdContainer5")
    public void firstMq5(String msg) {
        log.info("dev5 receive msg: {}", msg);
    }

//    @RabbitListener(queues = {"tbd_resume_id_rc_5"}, containerFactory = "secondContainerFactory")
//    public void secondMq(String msg) {
//        log.info("rc receive msg: {}", msg);
//    }
}
