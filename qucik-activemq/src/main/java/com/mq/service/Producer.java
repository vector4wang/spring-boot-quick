package com.mq.service;

import javafx.beans.property.SimpleStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;


import javax.jms.JMSException;


@Service
public class Producer {


    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsTemplate jmsTemplate;

    // TODO
    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(String destination, final String message) {
        jmsTemplate.convertAndSend(destination, message, new MessagePostProcessor() {
            @Override
            public javax.jms.Message postProcessMessage(javax.jms.Message message) throws JMSException {
//                message.setStringProperty(org.apache.activemq.artemis.core.message.impl.HDR_DUPLICATE_DETECTION_ID
//                        ,"1234");
                return message;
            }
        });
    }
}