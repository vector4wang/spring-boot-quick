package com.shiro.quick.shiro.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import static com.shiro.quick.shiro.filter.HeaderFilter.HEADER_KEY;

public class ShiroUtil {
    public static String getHeaderKey(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request.getHeader(HEADER_KEY);
    }
}
