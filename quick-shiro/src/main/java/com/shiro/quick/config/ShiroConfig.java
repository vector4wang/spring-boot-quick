package com.shiro.quick.config;

import com.shiro.quick.filter.HeaderFilter;
import com.shiro.quick.shiro.realm.HeaderRealm;
import com.shiro.quick.shiro.realm.MyRealm;
import com.shiro.quick.shiro.realm.OtherRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * SessionManager 暂未集成
 */
@Configuration
public class ShiroConfig {


    /**
     * 自定义Realm，指定密码加密算法、加密次数等等
     *
     * @return
     */
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    @Bean
    public OtherRealm otherRealm() {
        OtherRealm otherRealm = new OtherRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA1");
        hashedCredentialsMatcher.setHashIterations(1);
        otherRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return otherRealm;
    }


    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        LinkedList<Realm> realmLinkedList = new LinkedList<>();
        realmLinkedList.add(myRealm());
        realmLinkedList.add(otherRealm());
        realmLinkedList.add(new HeaderRealm());

        modularRealmAuthenticator.setRealms(realmLinkedList);
        /**
         * 认证通过策略
         *
         * 所有领域、仅 1 个或多个领域、没有领域等都成功
         */
//        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());


        return modularRealmAuthenticator;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        /**
         * 多领域认证
         * defaultSecurityManager.setAuthenticator(authenticator());
         */
        defaultSecurityManager.setAuthenticator(authenticator());
//        defaultSecurityManager.setRealm(myRealm());// 单领域认证



        return defaultSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        /**
         * 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         */
        Map<String, Filter> filterMap = new HashMap<>();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("hf", new HeaderFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager());
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/unauthorized");

        Map<String, String> map = new LinkedHashMap<>();
        /**
         * {@link DefaultFilter}
         */
        map.put("/doLogin", "anon");
        map.put("/getLogin/**", "anon");
        map.put("/anno/hello1", "anon");
        map.put("/vip", "roles[admin]");
        map.put("/common", "roles[user]");
        // 登陆鉴权
//        map.put("/**", "authc");
//        map.put("/**", "jwt");
        map.put("/**", "hf,authc");
        // header 鉴权
        factoryBean.setFilterChainDefinitionMap(map);
        return factoryBean;
    }


    /**
     * 一下三个bean是为了让@RequiresRoles({"admin"})  生效
     * 这两个是 Shiro 的注解，我们需要借助 SpringAOP 扫描到它们
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
