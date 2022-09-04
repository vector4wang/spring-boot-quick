package com.shiro.quick.service;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

@Service
public class TestService {


    @RequiresRoles({"admin"})
    public String vipPrint() {
        return "i am vip";
    }

}
