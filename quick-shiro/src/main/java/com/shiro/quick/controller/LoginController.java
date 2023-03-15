package com.shiro.quick.controller;

import com.shiro.quick.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
public class LoginController {

    @Resource
    private TestService testService;


	@GetMapping("/getLogin/{username}/{password}")
	public String doLoginGet(@PathVariable("username") String username,@PathVariable("password") String password) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(username, password));
			System.out.println("登录成功!");
			return "redirect:/getIndex.html";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			System.out.println("登录失败!");
		}
		return "redirect:/login";
	}

    @PostMapping("/doLogin")
    public String doLogin(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("登录成功!");
            return "redirect:/index";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败!");
        }
        return "redirect:/login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        String wel = "hello ~ ";
//        Subject subject = SecurityUtils.getSubject();
//        System.out.println(subject.hasRole("admin"));
//        String s = testService.vipPrint();
//        wel = wel + s;
        return wel;
    }

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "please login!";
    }


    @GetMapping("/vip")
    @ResponseBody
    public String vip() {
        return "hello vip";
    }


    @GetMapping("/common")
    @ResponseBody
    public String common() {
        return "hello common";
    }
}
