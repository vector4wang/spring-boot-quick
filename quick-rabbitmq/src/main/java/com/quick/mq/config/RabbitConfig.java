package com.quick.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {


	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.port}")
	private int port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.publisher-confirms}")
	private boolean publisherConfirm;

	@Value("${rabbitmq.virtual-host}")
	private String virtualHost;



	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setPublisherConfirms(publisherConfirm); //必须要设置
		return connectionFactory;
	}

	//////////////////////////////针对消息 delay queue////////////////////////////////
	/**
	 * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
	 * 每个message可以控制自己的失效时间
	 */
	public final static String DELAY_QUEUE_MSG = "delay_queue";

	/**
	 * DLX
	 */
	public final static String DELAY_EXCHANGE_NAME = "delay_exchange";


	/**
	 * 正常消费的队列
	 */
	public final static String PROCESS_QUEUE = "process_queue";

	/**
	 * 正常队列对应的exchange
	 */
	public final static String PROCESS_EXCHANGE_NAME = "process_exchange";


	public static String ROUTING_KEY = "delay";

	/**
	 * 延迟队列 exchange
	 * @return
	 */
	@Bean
	public DirectExchange delayExchange() {
		return new DirectExchange(DELAY_EXCHANGE_NAME);
	}

	@Bean
	public DirectExchange processExchange() {
		return new DirectExchange(PROCESS_EXCHANGE_NAME);
	}

	/**
	 * 存放延迟消息的队列 最后将会转发给exchange(实际消费队列对应的)
	 * @return
	 */
	@Bean
	Queue delayQueue4Msg(){
		return QueueBuilder.durable(DELAY_QUEUE_MSG)
				.withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
				.withArgument("x-dead-letter-routing-key", ROUTING_KEY) // dead letter携带的routing key
				.build();
	}

	@Bean
	public Queue processQueue() {
		return QueueBuilder.durable(PROCESS_QUEUE)
				.build();
	}



	/**
	 * 将延迟队列与exchange绑定，即到达指定时间之后需要转交给queue消费
	 * @return
	 */
	@Bean
	Binding delayBinding() {
		return BindingBuilder.bind(delayQueue4Msg())
				.to(delayExchange())
				.with(ROUTING_KEY);
	}

	@Bean
	Binding queueBinding() {
		return BindingBuilder.bind(processQueue())
				.to(processExchange())
				.with(ROUTING_KEY);
	}

	//////////////////////////////delay////////////////////////////////


	//////////////////////////////针对队列delay////////////////////////////////

	/**
	 * 针对队列设置过期时间的队列
	 */
	public final static String DELAY_QUEUE_NAME = "delay_queue_queue";

	public final static String DELAY_QUEUE_EXCHANGE_NAME = "delay_queue_exchange";

	@Bean
	public DirectExchange delayQueueExchange() {
		return new DirectExchange(DELAY_QUEUE_EXCHANGE_NAME);
	}

	/**
	 * 存放消息的延迟队列 最后将会转发给exchange(实际消费队列对应的)
	 * @return
	 */
	@Bean
	public Queue delayQueue4Queue() {
		return QueueBuilder.durable(DELAY_QUEUE_NAME)
				.withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE_NAME) // DLX
				.withArgument("x-dead-letter-routing-key", ROUTING_KEY) // dead letter携带的routing key
				.withArgument("x-message-ttl", 3000) // 设置队列的过期时间
				.build();
	}

	@Bean
	Binding delayQueueBind() {
		return BindingBuilder.bind(delayQueue4Queue())
				.to(delayQueueExchange())
				.with(ROUTING_KEY);
	}



	@Bean
	public Queue helloQueue() {
		return new Queue("helloQueue");
	}

	@Bean
	public Queue msgQueue() {
		return new Queue("msgQueue");
	}

	//===============以下是验证topic Exchange的队列==========
	@Bean
	public Queue queueMessage() {
		return new Queue("topic.message");
	}

	@Bean
	public Queue queueMessages() {
		return new Queue("topic.messages");
	}
	//===============以上是验证topic Exchange的队列==========


	//===============以下是验证Fanout Exchange的队列==========
	@Bean
	public Queue AMessage() {
		return new Queue("fanout.A");
	}

	@Bean
	public Queue BMessage() {
		return new Queue("fanout.B");
	}

	@Bean
	public Queue CMessage() {
		return new Queue("fanout.C");
	}
	//===============以上是验证Fanout Exchange的队列==========


	@Bean
	TopicExchange exchange() {
		return new TopicExchange("exchange");
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}

	/**
	 * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
	 * @param queueMessage
	 * @param exchange
	 * @return
	 */
	@Bean
	Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
		return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
	}

	/**
	 * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
	 * @param queueMessages
	 * @param exchange
	 * @return
	 */
	@Bean
	Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
		return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
	}

	@Bean
	Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(AMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(BMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(CMessage).to(fanoutExchange);
	}


	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	//必须是prototype类型
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

}