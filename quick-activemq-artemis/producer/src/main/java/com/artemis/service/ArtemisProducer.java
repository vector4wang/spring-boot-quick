package com.artemis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;

import static org.apache.activemq.artemis.api.core.Message.HDR_DUPLICATE_DETECTION_ID;

@Component
public class ArtemisProducer {
	@Autowired
    JmsTemplate jmsTemplate;
	
	@Value("${jms.queue.destination}")
	String destinationQueue;
	
	public void send(String msg){
		jmsTemplate.convertAndSend(destinationQueue, msg);
	}
	public void send(Person p){
		jmsTemplate.convertAndSend(destinationQueue, p);
	}


	public void sendNoDuplicateMs(String msg){
        jmsTemplate.send(session -> {
            Message message = session.createMessage();
            message.setStringProperty(HDR_DUPLICATE_DETECTION_ID.toString(), msg);
            return message;
        });

	}
}