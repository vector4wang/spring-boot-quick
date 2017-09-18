package com.quick.mq.controller;

import com.quick.mq.model.Msg;
import com.quick.mq.scenes.entityTransfer.MsgSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/rabbit4")
@Api(value = "实体传输",description = "实体传输")
public class EntityController {

    @Autowired
    private MsgSender msgSender;

    @ApiOperation("实体类传输测试")
    @RequestMapping(value = "/msg",method = RequestMethod.POST)
    public void userTest(@RequestBody Msg msg) {
        for(int i=0;i<=100;i++){
            msgSender.send(msg);
        }

    }
}
