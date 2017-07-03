package com.quick.controller;

import com.quick.entity.City;
import com.quick.mapper.CityMapper;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/18
 * Time: 10:14
 * Description:
 */
@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private CityMapper cityMapper;

    @RequestMapping("/hello")
    public ResponseEntity<?> hello(){
        City city = cityMapper.selectByPrimaryKey(12);
        System.out.println(JSONObject.fromObject(city).toString());
        return new ResponseEntity<Object>("hello", HttpStatus.OK);
    }
}
