package com.monitor.thread.service;

import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 *
 * @author wangxc
 * @date: 2019/3/7 下午11:14
 *
 */
@Service
public class SpecialService {

	public void build() {
		try {
            System.out.println(Thread.currentThread().getName() + " ====>异步执行中。。。");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
