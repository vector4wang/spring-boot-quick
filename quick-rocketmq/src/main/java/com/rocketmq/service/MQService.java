package com.rocketmq.service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.rocketmq.util.AliyunMessageConsumer;
import com.rocketmq.util.AliyunMessageProducer;
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

    @PostConstruct
    public void initMQListener() {
        AliyunMessageConsumer.subscribe();
    }

    public void sendMsg(String msg) {

        for(int i=0;i<1000;i++) {
            String tmp = "{\"name\":\"msg"+i+"\",\"v1.0\":\"2014-07-02 22:05 工具上线\",\"v2.0\":\"2016-11-16 14:13 增加php, go类生成\",\"v2.1\":\"2016-11-19 01:17 增加java类生成\"}";
            Message message = new Message("resume_update_rzero","TALENTID", UUID.randomUUID().toString(),tmp.getBytes());
            SendResult sendResult = AliyunMessageProducer.sendMsg("PID-Resume", message);
        }
    }
}
