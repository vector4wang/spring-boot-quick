package com.quick.auth.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${resource.id:spring-boot-application}") // 默认值spring-boot-application
	private String resourceId;

	@Value("${access_token.validity_period:3600}") // 默认值3600
	int accessTokenValiditySeconds = 3600;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.tokenStore(tokenStore());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')");
		oauthServer.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("normal-app")
				.authorizedGrantTypes("authorization_code", "implicit")
				.authorities("ROLE_CLIENT")
				.scopes("read", "write")
				.resourceIds(resourceId)
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.and()
				.withClient("trusted-app")
				.authorizedGrantTypes("client_credentials", "password")
				.authorities("ROLE_TRUSTED_CLIENT")
				.scopes("read", "write")
				.resourceIds(resourceId)
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.secret("secret");
	}


	/**
	 * token store
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
}