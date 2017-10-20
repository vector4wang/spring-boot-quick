package com.quick.druid.controller;

import com.quick.druid.entity.City;
import com.quick.druid.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Controller
public class ApiController {

    @Resource
    private CityService cityService;


    @RequestMapping("/city/{id}")
    public City getCityById(@PathVariable("id")int id){
        return cityService.getCityById(id);
    }

    @RequestMapping("/transaction/save")
    public String saveTransaction() {
        cityService.saveTransaction();
        return "saveTransaction";
    }

}
