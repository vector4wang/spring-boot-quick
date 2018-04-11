package com.dynamic.bean.mq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class Consumer {

    @JmsListener(destination = "${jsa.activemq.queue.name}",concurrency = "1")
    public void receiveQueue1(String message) throws InterruptedException {
        System.out.println("Consumer1收到的报文为: ["+ message + "]");
    }
}
