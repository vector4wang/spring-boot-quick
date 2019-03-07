package com.quick.jwt.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wangxc
 * @date: 2019/2/27 下午10:23
 *
 */
@RestController
@RequestMapping("/app")
@Api(value = "业务app")
public class AppController {

	@RequestMapping("/test")
	public String test() {
		return "hello world!";
	}
}
