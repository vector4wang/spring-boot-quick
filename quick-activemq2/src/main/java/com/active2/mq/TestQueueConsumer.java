package com.active2.mq;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class TestQueueConsumer {
    private final static Logger logger = Logger.getLogger(TestQueueConsumer.class);


    @JmsListener(destination = "${jsa.activemq.queue.name_1}", containerFactory = "jmsQueueListener1")
    public void receiveQueue1(final TextMessage text, Session session)
            throws JMSException {
        doMsg(text, session, "Consumer1收到的报文为:" + text.getText());
    }

    @JmsListener(destination = "${jsa.activemq.queue.name_2}", containerFactory = "jmsQueueListener2")
    public void receiveQueue2(final TextMessage text, Session session)
            throws JMSException {
        doMsg(text, session, "Consumer2收到的报文为:" + text.getText());
    }

    @JmsListener(destination = "${jsa.activemq.queue.name_3}", containerFactory = "jmsQueueListener3")
    public void receiveQueue3(final TextMessage text, Session session)
            throws JMSException {
        doMsg(text, session, "Consumer3收到的报文为:" + text.getText());
    }

    @JmsListener(destination = "${jsa.activemq.queue.name_4}", containerFactory = "jmsQueueListener4")
    public void receiveQueue4(final TextMessage text, Session session)
            throws JMSException {
        doMsg(text, session, "Consumer4收到的报文为:" + text.getText());
    }

    @JmsListener(destination = "${jsa.activemq.queue.name_5}", containerFactory = "jmsQueueListener5")
    public void receiveQueue5(final TextMessage text, Session session)
            throws JMSException {
        doMsg(text, session, "Consumer5收到的报文为:" + text.getText());
    }

    private void doMsg(TextMessage text, Session session, String message) throws JMSException {
        try {
            logger.info(text.getText());
            text.acknowledge();// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发
        } catch (Exception e) {
            session.recover();// 此不可省略 重发信息使用
        }
    }


}
