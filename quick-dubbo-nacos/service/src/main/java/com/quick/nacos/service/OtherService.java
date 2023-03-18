package com.quick.nacos.service;

import com.quick.nacos.provider.OtherProvider;
import org.apache.dubbo.config.annotation.DubboService;

/**
 *
 * @author wangxc
 * @date: 2022/3/6 10:49 AM
 *
 */
@DubboService(version = "${service.version}")
public class OtherService implements OtherProvider {
	@Override
	public String otherHello(String param) {
		return "other hello " + param;
	}
}
