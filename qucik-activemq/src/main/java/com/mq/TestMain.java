package com.mq;

import com.alibaba.fastjson.JSON;
import com.mq.model.TalentMQMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/20
 * Time: 17:57
 * Description:
 */
public class TestMain {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数是是否是事务型消息，设置为true,第二个参数无效
            //第二个参数是
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            //时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            //待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("localhost_queue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢

            for(int i=0;i<100;i++){
                TalentMQMessage mqMessage = new TalentMQMessage();
                mqMessage.setTalentId(UUID.randomUUID().toString());
                mqMessage.setCallbackData("haha");
                mqMessage.setCallbackQueue(i+"");
                producer.send(session.createObjectMessage(JSON.toJSONString(mqMessage)));
            }
            producer.close();
            System.out.println("呵呵");
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            while (true) {
                // 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
                TextMessage message = (TextMessage) consumer.receive(500000);
                if (null != message) {
                    System.out.println("收到消息" + message.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
}
