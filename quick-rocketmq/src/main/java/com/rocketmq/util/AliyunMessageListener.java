package com.rocketmq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:38
 * Description:
 */
public class AliyunMessageListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String body = new String(message.getBody());
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(jsonObject.getString("name") + "->" +System.currentTimeMillis());
        return Action.CommitMessage;
    }
}
