//package com.shiro.quick.config;
//
//import com.shiro.quick.filter.AuthFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterRegistration;
//
//@Component
//public class FilterConfig {
//
//    @Autowired
//    private AuthFilter authFilter;
//
//    @Bean
//    public FilterRegistrationBean<Filter> registrationFilter() {
//        //通过FilterRegistrationBean实例设置优先级可以生效
//        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
//        bean.setFilter(authFilter);//注册自定义过滤器
//        bean.setName("authFilter");//过滤器名称
//        bean.addUrlPatterns("/*");//过滤所有路径
//        bean.setOrder(Integer.MIN_VALUE);//优先级，最顶级
//        return bean;
//    }
//}
