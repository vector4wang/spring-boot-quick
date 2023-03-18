package com.quick.nacos.service;

import com.quick.nacos.provider.HelloProvider;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author wangxc
 * @date: 2022/3/5 10:18 PM
 *
 */
@DubboService(version = "${service.version}")
public class HelloServiceImpl implements HelloProvider {

	@Value("${service.name}")
	private String serviceName;

	@Override
	public String hello(String param) {
		RpcContext rpcContext = RpcContext.getContext();
		return String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s", serviceName,
				rpcContext.getLocalPort(), rpcContext.getMethodName(), serviceName, param);
	}
}
