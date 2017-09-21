package com.quick.exception.controller;

import com.quick.exception.service.ExceptionService;
import com.quick.exception.utils.BaseResp;
import com.quick.exception.utils.ResultStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    private ExceptionService exceptionService;

    @RequestMapping("/hello")
    public BaseResp<?> hello(@RequestParam("fz") int fz,
                             @RequestParam("fm") int fm){

        return new BaseResp<Double>(ResultStatus.SUCCESS,exceptionService.getResult(fz,fm));
    }
}
