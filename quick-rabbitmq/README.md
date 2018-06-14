# 延迟队列相关介绍

### Default Exchange
这种是特殊的Direct Exchange，是rabbitmq内部默认的一个交换机。该交换机的name是空字符串，所有queue都默认binding 到该交换机上。所有binding到该交换机上的queue，routing-key都和queue的name一样。

### Topic Exchange
通配符交换机，exchange会把消息发送到一个或者多个满足通配符规则的routing-key的queue。其中*表号匹配一个word，#匹配多个word和路径，路径之间通过.隔开。如满足a.*.c的routing-key有a.hello.c；满足#.hello的routing-key有a.b.c.helo。

### Fanout Exchange
扇形交换机，该交换机会把消息发送到所有binding到该交换机上的queue。这种是publisher/subcribe模式。用来做广播最好。
所有该exchagne上指定的routing-key都会被ignore掉。

### Header Exchange
设置header attribute参数类型的交换机。

延迟分两种
 
 - 在msg上设置过期时间
 - 在队列上设置过期时间

[![WX20180613-233153@2x.png](https://i.loli.net/2018/06/13/5b213948c6e1a.png)](https://i.loli.net/2018/06/13/5b213948c6e1a.png)


如上图创建三个exchange和三个队列

```java
@Bean
public DirectExchange delayExchange() {
	return new DirectExchange(DELAY_EXCHANGE_NAME);
}

@Bean
public DirectExchange processExchange() {
	return new DirectExchange(PROCESS_EXCHANGE_NAME);
}

@Bean
public DirectExchange delayQueueExchange() {
	return new DirectExchange(DELAY_QUEUE_EXCHANGE_NAME);
}

/**
 * 存放延迟消息的队列 最后将会转发给exchange(实际消费队列对应的)
 * @return
 */
@Bean
Queue delayQueue4Msg(){
	return QueueBuilder.durable(DELAY_QUEUE_MSG)
			.withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE_NAME) 
			.withArgument("x-dead-letter-routing-key", ROUTING_KEY) 
			.build();
}

@Bean
public Queue processQueue() {
	return QueueBuilder.durable(PROCESS_QUEUE)
			.build();
}

/**
 * 存放消息的延迟队列 最后将会转发给exchange(实际消费队列对应的)
 * @return
 */
@Bean
public Queue delayQueue4Queue() {
	return QueueBuilder.durable(DELAY_QUEUE_NAME)
			.withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE_NAME) // DLX
			.withArgument("x-dead-letter-routing-key", ROUTING_KEY) 
			.withArgument("x-message-ttl", 3000) // 设置队列的过期时间 单位毫秒
			.build();
}
```

接下来将每个exchange和对应的mq绑定
```java
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
@Bean
Binding delayQueueBind() {
	return BindingBuilder.bind(delayQueue4Queue())
			.to(delayQueueExchange())
			.with(ROUTING_KEY);
}
```

发送消息的方式
```java
public void sendDelayMsg(Msg msg) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	System.out.println(msg.getId() + " 延迟消息发送时间:" + sdf.format(new Date()));
	rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE_NAME, "delay", msg, new MessagePostProcessor() {
		@Override
		public Message postProcessMessage(Message message) throws AmqpException {
			message.getMessageProperties().setExpiration(msg.getTtl() + "");
			return message;
		}
	});
}

public void sendDelayQueue(Msg msg) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	System.out.println(msg.getId() + " 延迟队列消息发送时间:" + sdf.format(new Date()));
	rabbitTemplate.convertAndSend(RabbitConfig.DELAY_QUEUE_EXCHANGE_NAME,"delay",  msg);
}
```
