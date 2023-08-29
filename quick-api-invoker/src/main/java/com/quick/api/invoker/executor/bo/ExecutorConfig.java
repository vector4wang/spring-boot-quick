package com.quick.api.invoker.executor.bo;

import lombok.Data;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

@Data
public class ExecutorConfig {
    private int timeout = 5000;
    private Map<String,String> header;

    // Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, 80));

    private Proxy proxy;
}
