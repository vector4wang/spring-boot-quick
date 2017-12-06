package com.rocketmq.service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.rocketmq.config.AliyunAccessKeyBean;
import com.rocketmq.util.AliyunMessageConsumer;
import com.rocketmq.util.AliyunMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Autowired
    private AliyunAccessKeyBean aliyunAccessKeyBean;

    @PostConstruct
    public void initMQListener() {
        AliyunMessageConsumer.subscribe();
    }

    public void sendMsg(String msg) {
        for(int i=0;i<1000;i++) {
            Message message = new Message("resume_update_rzero","TALENTID", UUID.randomUUID().toString(),msg.getBytes());
            SendResult sendResult = AliyunMessageProducer.sendMsg("PID-Resume", message);
            System.out.println(sendResult.getMessageId());
        }

    }
}
