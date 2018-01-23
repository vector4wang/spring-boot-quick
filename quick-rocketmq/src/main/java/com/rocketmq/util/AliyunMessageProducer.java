package com.rocketmq.util;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.OrderProducer;

import java.util.Date;
import java.util.Properties;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:34
 * Description:
 */
public class AliyunMessageProducer {

    private static final String ACCESS_KEY = "LTAIcV7Ho2KS9a64";
    private static final String SECRET_KEY = "3FWmAyWC99S3D8a3iVYzBZ0qiD4fOJ";
    private static final String ONSAddr = "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";

    public static OrderProducer producer;




    /**
     * 获取消息的 Producer
     * 设置为单例
     * @param producerId producerId
     * @return Producer
     */
    public static OrderProducer getProducer(String producerId) {
        if (producer == null) {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.ProducerId, producerId);
            properties.put(PropertyKeyConst.AccessKey, ACCESS_KEY);
            properties.put(PropertyKeyConst.SecretKey, SECRET_KEY);
            properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
            properties.put(PropertyKeyConst.ONSAddr,ONSAddr);
            producer = ONSFactory.createOrderProducer(properties);
            // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
            producer.start();


        }
        return producer;
    }

    /**
     * 发布消息
     * @param producerId
     * @param msg
     * @return
     */
    public static SendResult sendMsg(String producerId,String sharding,Message msg) {
        OrderProducer producer = getProducer(producerId); //你申请的producerId
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg,sharding);
            // 发送消息，只要不抛异常就是成功
            if (sendResult != null) {
                System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
            }
        }
        catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
            e.printStackTrace();
        }
        return sendResult;
    }



}
