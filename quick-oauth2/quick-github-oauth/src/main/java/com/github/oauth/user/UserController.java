package com.github.oauth.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    public String info(Principal principal, ModelMap modelMap){
        String name = principal.getName();
        modelMap.put("name", name);

        return "user/info";
    }
}