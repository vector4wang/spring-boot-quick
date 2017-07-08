package com.quick.mq.controller;

import com.quick.mq.scenes.topicExChange.TopicSender;
import io.swagger.annotations.Api;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@RestController
@RequestMapping("/rabbit5")
@Api(value = "topic exchange类型rabbitmq",description = "")
public class TopicExchangeController {

    @Autowired
    private TopicSender topicSender;

    @RequestMapping(value = "/topic",method = RequestMethod.GET)
    public void topicTest() {
        topicSender.send();
    }
}
