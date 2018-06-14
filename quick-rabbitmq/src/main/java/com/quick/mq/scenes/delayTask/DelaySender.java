package com.quick.mq.scenes.delayTask;

import com.quick.mq.config.RabbitConfig;
import com.quick.mq.model.Msg;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Component
public class DelaySender {
	@Autowired
	private AmqpTemplate rabbitTemplate;


	/**
	 * 在消息上设置时间
	 * @param msg
	 */
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

	/**
	 * 在队列上设置时间，则消息不需要任何处理
	 * @param msg
	 */
	public void sendDelayQueue(Msg msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(msg.getId() + " 延迟队列消息发送时间:" + sdf.format(new Date()));
		rabbitTemplate.convertAndSend(RabbitConfig.DELAY_QUEUE_EXCHANGE_NAME,"delay",  msg);
	}
}
