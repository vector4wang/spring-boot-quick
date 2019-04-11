package com.multi.rabbitmq.config;

import com.multi.rabbitmq.mq.RabbitMqConsumer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author vector
 * @date: 2019/4/11 0011 10:38
 */
@Configuration
public class RabbitMqConfiguration {

    private int[] queuesConcurrency = {2,2,2,2,2,2};

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "firstConnectionFactory")
    @Primary
    public ConnectionFactory firstSyncConnectionFactory(
            @Value("${spring.rabbitmq.first.host}") String host,
            @Value("${spring.rabbitmq.first.port}") int port,
            @Value("${spring.rabbitmq.first.username}") String username,
            @Value("${spring.rabbitmq.first.password}") String password,
            @Value("${spring.rabbitmq.first.virtual-host}") String virtualHost) {


        return getCachingConnectionFactory(host, port, username, password, virtualHost);
    }

//    @Bean(name = "firstContainerFactory")
//    public SimpleRabbitListenerContainerFactory firstFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer,
//            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(1);
//        factory.setReceiveTimeout(1000L);
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }

    /**
     * 动态创建队列对应的消费端，可以自动分配并发数
     * @return
     */
    @Bean
    public Runnable dynamic4TalentMQConfiguration(){
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        for (int i = 0; i < queuesConcurrency.length; i++) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SimpleRabbitListenerContainerFactory.class);
            /**
             * 设置属性
             */
            beanDefinitionBuilder.addPropertyValue("connectionFactory", applicationContext.getBean("firstConnectionFactory"));
            beanDefinitionBuilder.addPropertyValue("concurrentConsumers", queuesConcurrency[i]);
            beanDefinitionBuilder.addPropertyValue("receiveTimeout", 1000L);

            /**
             * 注册到spring容器中
             */
            beanFactory.registerBeanDefinition("jmsQueue4TalentIdContainer" + i, beanDefinitionBuilder.getBeanDefinition());
        }
        return null;
    }

    @Bean
    public RabbitMqConsumer initRabbitMqConsumer() {
        return new RabbitMqConsumer();
    }


    @Bean(name = "firstRabbitTemplate")
    public RabbitTemplate firstRabbitTemplate(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
        //使用外部事物
        //ydtRabbitTemplate.setChannelTransacted(true);
        return firstRabbitTemplate;
    }

    @Bean(name = "secondConnectionFactory")
    public ConnectionFactory secondConnectionFactory(
            @Value("${spring.rabbitmq.second.host}") String host,
            @Value("${spring.rabbitmq.second.port}") int port,
            @Value("${spring.rabbitmq.second.username}") String username,
            @Value("${spring.rabbitmq.second.password}") String password,
            @Value("${spring.rabbitmq.second.virtual-host}") String virtualHost) {
        return getCachingConnectionFactory(host, port, username, password, virtualHost);
    }

    @Bean(name = "secondRabbitTemplate")
    @Primary
    public RabbitTemplate secondRabbitTemplate(
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate secondRabbitTemplate = new RabbitTemplate(connectionFactory);
        //使用外部事物
        //lpzRabbitTemplate.setChannelTransacted(true);
        return secondRabbitTemplate;
    }

    @Bean(name = "secondContainerFactory")
    public SimpleRabbitListenerContainerFactory secondFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    private CachingConnectionFactory getCachingConnectionFactory(String host,
                                                                 int port,
                                                                 String username,
                                                                 String password,
                                                                 String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }
}
