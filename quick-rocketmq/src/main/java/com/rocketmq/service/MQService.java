package com.rocketmq.service;

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
//    public void initMQListener() {
//        AliyunMessageConsumer.subscribe();
//    }

    public void sendMsg(String msg) {
        Message message = new Message("resume_update_rzero", "TALENTID", UUID.randomUUID().toString(), msg.getBytes());
        SendResult sendResult = AliyunMessageProducer.sendMsg("PID-Resume", message);
    }
}
