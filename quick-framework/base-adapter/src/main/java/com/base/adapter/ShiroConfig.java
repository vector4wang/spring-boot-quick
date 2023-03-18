package com.base.adapter;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 01390942
 * @Description
 * @create 2022/1/25
 * @since 1.0.0
 */
public class ShiroConfig {

	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	@Bean
	public SecurityManager securityManager(SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(new AuthorizingRealm() {
			protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
				return new SimpleAuthorizationInfo();
			}

			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
					throws AuthenticationException {
				return new SimpleAuthenticationInfo();
			}
		});
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}
	//	@Bean({"redisSessionDAO"})
	//	public RedisSessionDAO redisSessionDAO() {
	//		RedisSessionDAO rsd = new RedisSessionDAO();
	//		return rsd;
	//	}
	//	@Bean({"sessionManager"})
	//	public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
	//		DefaultWebSessionManager sm = new DefaultWebSessionManager();
	//		sm.setSessionDAO(redisSessionDAO);
	//		sm.setSessionValidationInterval(10800000L);
	//		sm.setGlobalSessionTimeout(10800000L);
	//		sm.setSessionValidationSchedulerEnabled(Boolean.FALSE);
	//		sm.setSessionIdUrlRewritingEnabled(Boolean.FALSE);
	//		sm.setSessionIdCookieEnabled(true);
	//		sm.setSessionIdCookie(this.sessionIdCookie());
	//		return sm;
	//	}

	private SimpleCookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie();
		cookie.setName("JSESSIONID");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(10800000);
		return cookie;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/*.html", "anon");
		filterChainDefinitionMap.put("/*.ico", "anon");
		filterChainDefinitionMap.put("/ajaxLogin", "anon");
		filterChainDefinitionMap.put("/login/**", "anon");
		//		if (StringUtils.isNotBlank(this.anonUrls)) {
		//			String[] anonUrlsList = StringUtils.splitByWholeSeparator(this.anonUrls, ",");
		//			String[] var5 = anonUrlsList;
		//			int var6 = anonUrlsList.length;
		//			for (int var7 = 0; var7 < var6; ++var7) {
		//				String s = var5[var7];
		//				filterChainDefinitionMap.put(s, "anon");
		//			}
		//		}
		filterChainDefinitionMap.put("/sync/**", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/captcha/**", "anon");
		filterChainDefinitionMap.put("/doc.html/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/v2/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/configuration/ui/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/configuration/security/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}