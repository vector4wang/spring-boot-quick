package com.quick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/10
 * Time: 9:49
 * Description:
 */
@Controller
public class HelloController {
    @RequestMapping("/helloHtml")
    public String helloHtml(Map<String,Object> map){

        map.put("hello","from TemplateController.helloHtml");
        return"/hello";
    }
}
