package com.shiro.config;

import com.shiro.shiro.filter.CustomFormAuthenticationFilter;
import com.shiro.shiro.realm.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangxc
 * @date: 2019-12-01 22:30
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setLoginUrl("/user/loginUrl"); // 需要登陆时重定向的一个页面地址
        factoryBean.setSuccessUrl("/user/success");
        factoryBean.setUnauthorizedUrl("/unauthorized");
        factoryBean.setSecurityManager(manager());

        // org.apache.shiro.web.filter.mgt.DefaultFilter
        HashMap<String, String> filterChainDefinitionMap = new LinkedHashMap();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/success", "authc");
        filterChainDefinitionMap.put("/unauthorized","anon" );
        filterChainDefinitionMap.put("/**", "authc");

//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("customFormAuthenticationFilter", new CustomFormAuthenticationFilter());
//        factoryBean.setFilters(filters);

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    @Bean(name = "manager")
    public SessionsSecurityManager manager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(realm());// 单领域认证
        return defaultSecurityManager;

    }

	//注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
	@Bean
	public MyShiroRealm realm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashIterations(100);
		hashedCredentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
		return myShiroRealm;
	}





}
