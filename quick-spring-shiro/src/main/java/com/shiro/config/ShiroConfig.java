package com.shiro.config;

import com.shiro.shiro.MyShiroRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author wangxc
 * @date: 2019-12-01 22:30
 *
 */
@Configuration
public class ShiroConfig {

	//注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
	@Bean
	public Realm realm() {
		return new MyShiroRealm();
	}

	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		/**
		 * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
		 * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
		 * 加入这项配置能解决这个bug
		 */
//		creator.setUsePrefix(true);
		return creator;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		// 由于展示统一使用注解做访问控制，所以这里配置所有请求路径都可以匿名访问
		chainDefinition.addPathDefinition("/**", "anon"); // all paths are managed via annotations
		return chainDefinition;

	}


}
