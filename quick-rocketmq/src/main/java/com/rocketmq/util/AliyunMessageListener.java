package com.rocketmq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:38
 * Description:
 */
public class AliyunMessageListener implements MessageOrderListener {


    @Override
    public OrderAction consume(Message message, ConsumeOrderContext consumeOrderContext) {
        String body = new String(message.getBody());
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(message.getShardingKey() + "分区 " + jsonObject.getString("talentId") + "->" +System.currentTimeMillis());
        /**
         * 消息消费处理失败或者处理出现异常，返回OrderAction.Suspend<br>
         */
        return OrderAction.Success;
    }
}
