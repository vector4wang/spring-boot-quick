package com.quick.mq.scenes.topicExChange;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Component
@RabbitListener(queues = "topic.messages")
public class TopicMessagesReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("topicMessagesReceiver  : " +msg);
    }
}
