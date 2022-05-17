package com.quick.db.crypt.controller;


import cn.hutool.json.JSONUtil;
import com.quick.db.crypt.entity.User;
import com.quick.db.crypt.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class TestApi {


    @Resource
    private UserService userService;

    @GetMapping("/info")
    public String getInfo() {

        User user = userService.queryById(1);
        return JSONUtil.toJsonStr(user);
    }
}