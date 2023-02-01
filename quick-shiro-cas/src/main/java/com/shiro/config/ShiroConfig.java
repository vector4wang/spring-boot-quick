package com.shiro.config;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author wangxc
 * @date: 2022/9/5 21:15
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 客户端项目地址
	 */
	@Value("${cas.project.url}")
	private String projectUrl;

	/**
	 * CAS服务器地址
	 */
	@Value("${cas.server.url}")
	private String casServerUrl;

	/** 客户端名称 */
	@Value("${cas.client-name}")
	private String clientName;

	@Bean("securityManager")
	public DefaultWebSecurityManager securityManager(Pac4jSubjectFactory subjectFactory, SessionManager sessionManager,
			CasRealm casRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(casRealm);
		manager.setSubjectFactory(subjectFactory);
		manager.setSessionManager(sessionManager);
		return manager;
	}

	@Bean
	public CasRealm casRealm() {
		CasRealm realm = new CasRealm();
		// 使用自定义的realm
		realm.setClientName(clientName);
		realm.setCachingEnabled(false);
		//暂时不使用缓存
		realm.setAuthenticationCachingEnabled(false);
		realm.setAuthorizationCachingEnabled(false);
		return realm;
	}

	/**
	 * 使用 pac4j 的 subjectFactory
	 * @return
	 */
	@Bean
	public Pac4jSubjectFactory subjectFactory() {
		return new Pac4jSubjectFactory();
	}

	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistration = new FilterRegistrationBean<DelegatingFilterProxy>();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
		return filterRegistration;
	}

	/**
	 * 加载shiroFilter权限控制规则
	 * @param shiroFilterFactoryBean
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/", "securityFilter");
		filterChainDefinitionMap.put("/index", "securityFilter");
		filterChainDefinitionMap.put("/callback", "callbackFilter");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "anon");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}


	/**
	 * shiroFilter
	 * @param securityManager
	 * @param config
	 * @return
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, Config config) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		// 添加casFilter到shiroFilter中
		loadShiroFilterChain(shiroFilterFactoryBean);
		Map<String, Filter> filters = new HashMap<>(4);
		//cas 资源认证拦截器
		SecurityFilter securityFilter = new SecurityFilter();
		securityFilter.setConfig(config);
		securityFilter.setClients(clientName);
		filters.put("securityFilter", securityFilter);
		//cas 认证后回调拦截器
		CallbackFilter callbackFilter = new CallbackFilter();
		callbackFilter.setConfig(config);
		callbackFilter.setDefaultUrl(projectUrl);
		filters.put("callbackFilter", callbackFilter);
//		 注销 拦截器
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setConfig(config);
		logoutFilter.setCentralLogout(true);
		logoutFilter.setLocalLogout(true);
		//添加logout后  跳转到指定url  url的匹配规则  默认为 /.*;
		logoutFilter.setLogoutUrlPattern(".*");
		logoutFilter.setDefaultUrl(projectUrl + "/callback?client_name=" + clientName);
		filters.put("logout", logoutFilter);
		shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SessionDAO sessionDAO() {
		return new MemorySessionDAO();
	}

	/**
	 * 自定义cookie名称
	 * @return
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		return cookie;
	}

	@Bean
	public DefaultWebSessionManager sessionManager(SimpleCookie sessionIdCookie, SessionDAO sessionDAO) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookie(sessionIdCookie);
		sessionManager.setSessionIdCookieEnabled(true);
		//30分钟
		sessionManager.setGlobalSessionTimeout(180000);
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}
}
