package com.quick.mulit.controller;

import com.quick.mulit.entity.primary.City;
import com.quick.mulit.entity.secondary.Reader;
import com.quick.mulit.service.CityService;
import com.quick.mulit.service.ReaderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/8/22
 * Time: 18:08
 * Description:
 */
@RestController
public class ApiController {

    @Resource
    private CityService cityService;

    @Resource
    private ReaderService readerService;

    @RequestMapping("/getCity")
    public City getUsers() {
        City city = cityService.getCity((short) 1);
        return city;
    }

    @RequestMapping("/getReader")
    public Reader getReader() {
        Reader reader = readerService.getReader(1);
        return reader;
    }
}
