package com.quick.controller;

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

    @RequestMapping("/hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<Object>("hello", HttpStatus.OK);
    }
}
