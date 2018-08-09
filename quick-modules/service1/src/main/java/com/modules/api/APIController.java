package com.modules.api;


import com.modules.entity.City;
import com.modules.service.CityService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/1
 * Time: 8:36
 */
@RestController
@RequestMapping("/service1")
public class APIController {

    @Resource
    private CityService cityService;

    @RequestMapping("/hello/{id}")
    public City getCity(@PathVariable(value = "id",required = false)Integer id){
        return  cityService.selectById(id);
    }

}
