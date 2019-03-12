package com.active2.config;

import com.active2.mq.SimpeQueueConsumer;
import com.active2.mq.TestQueueConsumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/4/26 0026
 * Time: 10:28
 * Description:
 * AUTO_ACKNOWLEDGE = 1    自动确认
 * CLIENT_ACKNOWLEDGE = 2    客户端手动确认
 * DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
 * SESSION_TRANSACTED = 0    事务提交并确认
 * INDIVIDUAL_ACKNOWLEDGE = 4    单条消息确认 activemq 独有
 *
 *
 * 默认从上到下创建Bean
 */
@ConditionalOnProperty(value = "activemq.switch")
@EnableJms
@Configuration
public class AcitveMQConfig {

    private Logger logger = Logger.getLogger(this.getClass());

    @Value("${jsa.activemq.queue.names.concurrency}")
    private String queuesConcurrency;

    @Value("${jsa.activemq.simple.names.concurrency}")
    private String simpeQueuesConcurrency;

    @Autowired
    private ApplicationContext applicationContext;

	@Autowired
	private AbstractEnvironment environment;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("queue1");
    }



    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        logger.info("init redeliveryPolicy");
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);

        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${activemq.url}") String url, RedeliveryPolicy redeliveryPolicy) {
        logger.info("init activeMQConnectionFactory");
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        "admin",
                        "admin",
                        url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue) {
        logger.info("init jmsTemplate");
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDeliveryMode(2);//进行持久化配置 1表示非持久化，2表示持久化</span>
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式</span>
        return jmsTemplate;
    }

    /**
     *  有时间会使用下面方式创建多个类型相同不同beanname的bean，这里要注意相关的启动顺序
     */
    @Bean
    public Runnable dynamicConfiguration() throws Exception {
        logger.info("init jmsQueueListeners");
        String[] concurrencys = queuesConcurrency.split(",");
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        for (int i = 1; i <= 5; i++) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultJmsListenerContainerFactory.class);
            /**
             * 设置属性
             */
            beanDefinitionBuilder.addPropertyValue("connectionFactory", applicationContext.getBean("activeMQConnectionFactory"));
            beanDefinitionBuilder.addPropertyValue("concurrency", concurrencys[i-1]);
            beanDefinitionBuilder.addPropertyValue("recoveryInterval", 1000L);
            beanDefinitionBuilder.addPropertyValue("sessionAcknowledgeMode", 2);

            /**
             * 注册到spring容器中
             */
            beanFactory.registerBeanDefinition("jmsQueueListener" + i, beanDefinitionBuilder.getBeanDefinition());
        }
        return null;
    }

    @Bean
    public TestQueueConsumer consumer() {

        logger.info("init jmsQueueListener Consumer");
        return new TestQueueConsumer();
    }



    @Bean
    public Runnable dynamicConfiguration4Simpe() throws Exception {
        logger.info("init jmsSimpeQueueListeners");
        String[] concurrencys = simpeQueuesConcurrency.split(",");
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        for (int i = 1; i <= 5; i++) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultJmsListenerContainerFactory.class);
            /**
             * 设置属性
             */
            beanDefinitionBuilder.addPropertyValue("connectionFactory", applicationContext.getBean("activeMQConnectionFactory"));
            beanDefinitionBuilder.addPropertyValue("concurrency", concurrencys[i-1]);
            beanDefinitionBuilder.addPropertyValue("recoveryInterval", 1000L);
            beanDefinitionBuilder.addPropertyValue("sessionAcknowledgeMode", 2);

            /**
             * 注册到spring容器中
             */
            beanFactory.registerBeanDefinition("jmsSimpeQueueListener" + i, beanDefinitionBuilder.getBeanDefinition());
        }
        return null;
    }


}

