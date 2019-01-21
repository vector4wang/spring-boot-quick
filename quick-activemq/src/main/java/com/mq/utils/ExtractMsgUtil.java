package com.mq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.util.ByteSequence;
import org.apache.commons.io.IOUtils;

import javax.jms.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author vector
 * @date: 2019/1/21 0021 10:11
 */
public class ExtractMsgUtil {

    public static void main(String[] args) {
        try {
            getMQMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void getMQMsg() throws JMSException {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://x.x.x.x:61616");
        Connection connection =
                connectionFactory.createConnection("admin","admin");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queuename");
        QueueBrowser queueBrowser = session.createBrowser(queue);
        Enumeration msgs = queueBrowser.getEnumeration();
        List<String> temp = new ArrayList<>();
        while (msgs.hasMoreElements()) {
            try {
                ActiveMQTextMessage msg = (ActiveMQTextMessage)msgs.nextElement();
                JSONObject jsonObject = JSON.parseObject(msg.getText());
                temp.add(jsonObject.getString("ResumeId"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("save to file");
        try {
            IOUtils.writeLines(temp,"\n",new FileOutputStream(new File("D:\\activemq-resume-msg.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
