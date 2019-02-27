package com.wx.pn;

import com.wx.pn.api.config.ApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author vector
 * @date: 2018/11/2 0002 18:23
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class VoiceApplication {


    @Value("${weixin.app-id}")
    private String appKey;

    @Value("${weixin.app-secret}")
    private String appSecret;

    @Value("${weixin.token}")
    private String token;


    @Bean(name = "wxApiConfig")
    public ApiConfig initWX() {
        return new ApiConfig(appKey, appSecret, token);
    }


    public static void main(String[] args) {
        SpringApplication.run(VoiceApplication.class, args);
    }
}

