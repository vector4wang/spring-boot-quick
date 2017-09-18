package com.quick.mq.controller;

import com.quick.mq.scenes.callback.CallBackSender;
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
@RequestMapping("/rabbit7")
@Api(value = "带callback的消息",description = "带callback的消息")
public class CallbackController {

    @Autowired
    private CallBackSender callBackSender;

    @RequestMapping(value = "/callback",method = RequestMethod.GET)
    public void callbak() {
        callBackSender.send();
    }
}
