package com.quick.redis.controller;

import com.quick.redis.service.CompanyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class ApiController {

    @Resource
    private CompanyService companyService;

    @RequestMapping(value = "/isCompany/{company}", method = RequestMethod.GET)
    public Integer isCompamy(@PathVariable("company") String company) {
        return companyService.isCompany(company);
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer isCompamy() {
        return companyService.add();
    }
}
