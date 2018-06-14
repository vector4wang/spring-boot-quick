
### 开头

先熟悉下面会用到的一些名词~

- exchange： 交换机

- routingkey: 路由key

- queue: 队列

**exchange和queue是需要绑定在一起的，然后消息发送到exchange再由exchange通过routingkey发送到对应的队列中。**

[![mq.png](http://upload-images.jianshu.io/upload_images/3167229-6c4b5b9265af981d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/06/14/5b21e04409004.png)

(不是这张图~~~)

exchange分四种

#### Default Exchange
这种是特殊的Direct Exchange，是rabbitmq内部默认的一个交换机。该交换机的name是空字符串，所有queue都默认binding 到该交换机上。所有binding到该交换机上的queue，routing-key都和queue的name一样。

**注意： 这就是为什么你直接创建一个queue也能正常的生产与消费，因为对应的exchange是RabbitMQ默认的，routingkey就是该队列的名字**

#### Topic Exchange
通配符交换机，exchange会把消息发送到一个或者多个满足通配符规则的routing-key的queue。其中*表号匹配一个word，#匹配多个word和路径，路径之间通过.隔开。如满足a.*.c的routing-key有a.hello.c；满足#.hello的routing-key有a.b.c.helo。

#### Fanout Exchange
扇形交换机，该交换机会把消息发送到所有binding到该交换机上的queue。这种是publisher/subcribe模式。用来做广播最好。
所有该exchagne上指定的routing-key都会被ignore掉。

#### Header Exchange
设置header attribute参数类型的交换机。

简单的了解之后，下面就是延迟队列的实现方式

### 延迟队列的实现

延迟分两种
 
 - 在msg上设置过期时间
 - 在队列上设置过期时间

*一定要看懂这张图！！！*
[![WX20180613-233153@2x.png](http://upload-images.jianshu.io/upload_images/3167229-47316d92517586ef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/06/13/5b213948c6e1a.png)


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

### 验证结果

为每个消息设置过期时间
[![延迟消息.gif](http://upload-images.jianshu.io/upload_images/3167229-dbb16df8756346d6.gif?imageMogr2/auto-orient/strip)](https://i.loli.net/2018/06/14/5b2206367916d.gif)


为队列设置过期时间
[![延迟队列消息.gif](http://upload-images.jianshu.io/upload_images/3167229-f5fc967fdada17b9.gif?imageMogr2/auto-orient/strip)](https://i.loli.net/2018/06/14/5b2206367110f.gif)


*如果你把设置了过期时间的消息发送到设置了过期时间的队里中的时候，以最短的时间为准~~*





### 最后

其实我在实现的过程中也花了很长的时间，主要就是被exchange和queue搞乱掉了，最后索性自己画了个图，按照图来一个一个创建与绑定。之后就很清晰很容易的实现了。

>强调！！！ 如果在开发的过程中发现exchange和queue绑定错误了，建议从管理界面将queue和exchange unbind或者删除重新创建！

代码已上传到Github上[Here](https://github.com/vector4wang/spring-boot-quick/tree/master/quick-rabbitmq)


CSDN：http://blog.csdn.net/qqhjqs?viewmode=list 

博客：http://blog.wangxc.club

简书：https://www.jianshu.com/u/223a1314e818 

Github:https://github.com/vector4wang 

Gitee:https://gitee.com/backwxc 

如果感觉有帮助的话，点个赞哦~
