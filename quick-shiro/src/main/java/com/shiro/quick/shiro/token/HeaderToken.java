package com.shiro.quick.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

public class HeaderToken implements AuthenticationToken {

    private String key;

    public HeaderToken(String key) {
        this.key = key;
    }

    @Override
    public Object getPrincipal() {
        return key;
    }

    @Override
    public Object getCredentials() {
        return key;
    }
}
