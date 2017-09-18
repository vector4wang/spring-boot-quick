package com.modules.api;


import com.modules.domain.City;
import com.modules.service.CityService;
import net.sf.json.JSONObject;
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
    public JSONObject getCity(@PathVariable(value = "id",required = false)Integer id){
        JSONObject result = new JSONObject();
        if(id==null){
            result.accumulate("errMessage","请填写参数");
            return result;
        }
        City city = cityService.selectById(id);
        result.accumulate("data",city);
        return  result;
    }

}
