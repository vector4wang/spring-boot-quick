package com.mq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/21
 * Time: 10:10
 * Description:
 */
@Configuration
@EnableJms
public class ActiveMqConnectionFactorConfig {

    @Value("${jsa.activemq.queue.clientId}")
    private String clientId;

    @Value("${jsa.activemq.queue.clientId.concurrency}")
    private String concurrency;


    @Bean
    public JmsListenerContainerFactory<?> jsaFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setClientId(clientId);
        factory.setConcurrency(concurrency);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
