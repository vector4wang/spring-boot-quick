package com.rocketmq.util;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderConsumer;

import java.util.Date;
import java.util.Properties;

public class AliyunMessageConsumer {
    private static final String ACCESS_KEY = "xxxxx";
    private static final String SECRET_KEY = "xxxxx";
    private static final String TOPIC_NAME = "xxxxx"; //你申请的TopicName
    private static final String CONSUMER_ID = "xxxxx-xxxxx"; //你申请的ConsumerId
    private static final String ONSAddr = "xxxxx"; //你申请的ConsumerId
    private static final String Max_ReconsumeTimes = "20"; //消息消费失败时的最大重试次数

    /**
     * 订阅消息
     */
    public static void subscribe() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, CONSUMER_ID);
        properties.put(PropertyKeyConst.AccessKey, ACCESS_KEY);
        properties.put(PropertyKeyConst.SecretKey, SECRET_KEY);
        properties.put(PropertyKeyConst.ONSAddr,ONSAddr);
        properties.put(PropertyKeyConst.MaxReconsumeTimes, Max_ReconsumeTimes);
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(properties);
        consumer.subscribe(TOPIC_NAME, "*", new AliyunMessageListener());
        consumer.start();
        System.out.println(CONSUMER_ID + " is running @" + new Date());
    }
}