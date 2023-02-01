package com.shiro.config;

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.logout.DefaultCasLogoutHandler;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {

	/**
	 * cas服务地址
	 */
    @Value("${cas.server.url}")
    private String casServerUrl;

    /**
     * 客户端项目地址
     */
    @Value("${cas.project.url}")
    private String projectUrl;

    /** 相当于一个标志，可以随意 */
    @Value("${cas.client-name}")
    private String clientName;
    
    /**
     *  pac4j配置
     * @param casClient
     * @return
     */
    @Bean
    public Config config(CasClient casClient) {
        return new Config(casClient);
    }

    /**
     * cas 客户端配置
     * @param casConfig
     * @return
     */
    @Bean
    public CasClient casClient(CasConfiguration casConfig){
        CasClient casClient = new CasClient(casConfig);
        //客户端回调地址
        casClient.setCallbackUrl(projectUrl + "/callback?client_name=" + clientName);
        casClient.setName(clientName);
        return casClient;
    }

    /**
     * 请求cas服务端配置
     * @param casLogoutHandler
     */
    @Bean
    public CasConfiguration casConfig(){
        final CasConfiguration configuration = new CasConfiguration();
        //CAS server登录地址
        configuration.setLoginUrl(casServerUrl + "/login");
        configuration.setAcceptAnyProxy(true);
        configuration.setPrefixUrl(casServerUrl + "/");
        configuration.setLogoutHandler(new DefaultCasLogoutHandler<>());
        return configuration;
    }

}