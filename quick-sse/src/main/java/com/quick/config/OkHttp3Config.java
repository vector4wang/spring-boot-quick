package com.quick.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class OkHttp3Config {

    @Value("${proxy.hostname:127.0.0.1}")
    private String proxyHostname;

    @Value("${proxy.port:1080}")
    private int proxyPort;

    @Bean
    public OkHttpClient okHttpClient() {
        InetSocketAddress address = new InetSocketAddress(proxyHostname, proxyPort);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        OkHttpClient.Builder client = new OkHttpClient.Builder().proxy(proxy);
        /**
         * 忽略ssl校验
         */
        client.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        client.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        return client.build();
    }

}