package com.rocketmq.util;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Date;
import java.util.Properties;

public class AliyunMessageConsumer {
    private static final String ACCESS_KEY = "LTAIcV7Ho2KS9a64";
    private static final String SECRET_KEY = "3FWmAyWC99S3D8a3iVYzBZ0qiD4fOJ";
    private static final String TOPIC_NAME = "resume_update_rzero"; //你申请的TopicName
    private static final String CONSUMER_ID = "CID-ResumeUpdateRZero1"; //你申请的ConsumerId

    /**
     * 订阅消息
     */
    public static void subscribe() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, CONSUMER_ID);
        properties.put(PropertyKeyConst.AccessKey, ACCESS_KEY);
        properties.put(PropertyKeyConst.SecretKey, SECRET_KEY);
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(TOPIC_NAME, "*", new AliyunMessageListener());
        consumer.start();
        System.out.println(CONSUMER_ID + " is running @" + new Date());
    }
}