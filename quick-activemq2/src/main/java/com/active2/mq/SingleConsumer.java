package com.active2.mq;

import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author wangxc
 * @date: 2019/3/6 下午11:21
 *
 */
public class SingleConsumer {
	@JmsListener(destination = "${jsa.activemq.simple.queue.name_1}", containerFactory = "jmsSimpeQueueListener1")
	public void receiveQueue1(final TextMessage text, Session session)
			throws JMSException {
		doMsg(text, session, "Consumer1收到的报文为:" + text.getText());
	}

	private void doMsg(TextMessage text, Session session, String s) {
		System.out.println(text);
	}
}
