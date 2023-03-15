package com.shiro.quick.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class MultiFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String headerKey = request.getHeader("Authorization");
        log.info("请求的 Header 中藏有 headerKey {}", headerKey);
        if (StringUtils.isEmpty(headerKey)) {

        }else{

        }
        return false;
    }
}
