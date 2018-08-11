package com.mq.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Listener3 implements MessageListener {
    private ExecutorService threadPool = Executors.newFixedThreadPool(8);
    @Override
    public void onMessage(final Message message) {
    threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    try {
                        Thread.sleep(new Random().nextInt(2)*500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("接收线程..."+Thread.currentThread().getName()+"收到的消息是："+((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }               
            }
        }); 
    }
}