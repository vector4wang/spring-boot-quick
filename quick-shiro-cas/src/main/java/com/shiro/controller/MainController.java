package com.shiro.controller;

import com.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		return "index " + userService.getUsername();
	}

	@GetMapping("/callback")
	@ResponseBody
	public String hello() {
		Subject subject = SecurityUtils.getSubject();
		System.out.println("subject = " + subject);
		return "hello" + subject.getPrincipal();
	}

	@GetMapping("/logout")
	public String logout() {
		return "index";
	}

}
