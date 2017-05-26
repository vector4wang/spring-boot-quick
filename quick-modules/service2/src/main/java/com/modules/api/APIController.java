package com.modules.api;

import com.modules.service.CityService;
import net.sf.json.JSONObject;
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
@RequestMapping("/service2")
public class APIController {

    @Resource
    private CityService cityService;

    @RequestMapping("/hello")
    public JSONObject hello(){
        JSONObject result = new JSONObject();
        result.accumulate("data","this is service2");
        return  result;
    }

}
