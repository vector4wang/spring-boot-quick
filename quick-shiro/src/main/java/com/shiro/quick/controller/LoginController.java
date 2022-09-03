package com.shiro.quick.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
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
        return "wel!";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "please login!";
    }
}
