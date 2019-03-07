package com.monitor.thread.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wangxc
 * @date: 2019/3/7 下午11:15
 *
 */
@RestController
@RequestMapping("/")
public class ApiController {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "Hello there";
	}
}
