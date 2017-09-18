package com.quick.mq.controller;

import com.quick.mq.scenes.fanoutExChange.FanoutSender;
import io.swagger.annotations.Api;
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
@RequestMapping("/rabbit6")
@Api(value = "广播模式或者订阅模式",description = "广播模式或者订阅模式")
public class FanoutController {

    @Autowired
    private FanoutSender fanoutSender;

    @RequestMapping(value = "/fanout",method = RequestMethod.GET)
    public void fanoutTest() {
        fanoutSender.send();
    }
}
