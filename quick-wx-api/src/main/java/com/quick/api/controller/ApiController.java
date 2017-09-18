package com.quick.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.quick.api.service.NewService;
import com.quick.api.utils.BaseResp;
import com.quick.api.utils.ResultStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private NewService newService;

    @RequestMapping("/news")
    public BaseResp<JSONArray> news(){
        JSONArray news = newService.getNews();
        return new BaseResp<>(ResultStatus.SUCCESS,news);
    }

    @RequestMapping("/content")
    public BaseResp<String> getContent(@RequestParam("url")String url){
        String content = "404";
        try {
            content = newService.getContent(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BaseResp<>(ResultStatus.SUCCESS,content);
    }
}
