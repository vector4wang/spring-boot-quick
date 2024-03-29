package com.mq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class Producer {

    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    @Autowired
    private JmsTemplate jmsTemplate;

    // TODO
    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(String destination, final String message) {
//        jmsTemplate.convertAndSend(destination, message, msg -> {
//            msg.setStringProperty(HDR_DUPLICATE_DETECTION_ID.toString(),"123");
//            return msg;
//        });
        jmsTemplate.convertAndSend(destination, message);
    }
}