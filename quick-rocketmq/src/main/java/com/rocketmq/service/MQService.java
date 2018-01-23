package com.rocketmq.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.rocketmq.util.AliyunMessageProducer;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:12
 * Description:
 */
@Service
public class MQService {

//    @PostConstruct
//    public void init() {
//        AliyunMessageConsumer.subscribe();
//    }


    public void sendMsg(String msg) {
        String str[] = {"a78e001e-7ea2-41dc-926b-d8037cfd8286", "a78e001e-7ea2-4207-aab4-6356794fd145", "a78e001e-7ea2-4469-8df0-496408714ebd", "a78e001e-7ea2-4852-bb78-3bcd94a66631", "a78e001e-7ea2-4ad4-a266-b16fbd000024", "a78e001e-7ea2-4ad9-9191-a9d3dc90ecfe", "a78e001e-7ea2-4fee-9e58-acdd97f91647", "a78e001e-7ea3-408e-97f8-ca63068dfc11", "a78e001e-7ea3-417a-9b4d-58b5c31d4778", "a78e001e-7ea3-4224-82a5-4191919c18b8", "a78e001e-7ea3-49bd-b5dd-bc6c21b2aa6f", "a78e001e-7f5a-4571-8d86-a1bf64a4da4e", "a78e001e-7f5a-49cc-bf8d-9aaa4aa1e859", "a78e001e-802a-4e09-9bfa-ac9d0e6aefbb", "a78e001e-802a-4e0b-9162-6e8376eb5c96", "a78e001e-8063-44ad-a311-3fee1d94612d", "a78e001e-807b-4060-9a59-bd97793246f4", "a78e001e-807b-4725-8a2c-fa676c049740", "a78e001e-807c-4079-b558-485970470ad1", "a78e001e-8093-45a8-8c34-2b222dbdbab5", "a78e001e-80ec-49f7-89a2-7081a6432ffe", "a78e001e-80ed-41c6-978d-6c874ce5d03c", "a78e001e-8103-4015-a24e-fe195d09ebab", "a78e001e-8121-44a1-8500-1e0dbbd4444a", "a78e001e-8232-4422-8963-a342de924331", "a78e001e-8232-453b-aee6-99c6f1ca00b1", "a78e001e-8232-47a1-ab19-b9c9a884fc67", "a78e001e-82ec-472f-ae86-61b3da80603e", "a78e001e-82ec-4aaf-b2d4-d8fcc6cca015", "a78e001e-82ed-4070-8b21-96a8a762c681"};
        for (int i=0;i<str.length;i++) {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            jsonObject.put("talentId", str[i]);
            Message message = new Message("talent_process_rc", "TALENTID", jsonObject.toJSONString().getBytes());
            message.setKey(UUID.randomUUID().toString());

            SendResult sendResult = sendResult = AliyunMessageProducer.sendMsg("PID-Resume","ORDER_" + i%5, message);

            System.out.println(sendResult.getMessageId());
        }
    }
}
