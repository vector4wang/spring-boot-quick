package com.app.controller;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
 
    /**
     * 创建shiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
      
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**","authc");
//        filterMap.put("/update","authc");
 
        //使用通配的方式设置某一个目录的访问级别
       /* filterMap.put("/templates","authc");*/
 
        //修改访问被拦截过后的跳转页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
 
 
        return shiroFilterFactoryBean;
    }
 
 
 
 
 
    /**
     * 创建defaultWebSecurityManageer
     */
    @Bean(value = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联我们创建的realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
 
 
 
 
 
 
 
 
 
    /**
     * 创建realm
     */
    @Bean(value = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}