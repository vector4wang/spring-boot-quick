package com.monitor.thread.task;

import com.monitor.thread.service.SpecialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.management.monitor.Monitor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Component("mTask")
//@Scope("prototype")//非单例
public class MoniotrTask extends Thread {

	final static Logger logger = LoggerFactory.getLogger(MoniotrTask.class);

	//参数封装
	private Monitor monitor;

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	@Resource
	private SpecialService specialService;

	public SpecialService getSpecialService() {
		return specialService;
	}

	@Override
	public void run() {
        logger.info("线程:" + Thread.currentThread().getName() + "运行中.....");
        specialService.build();
	}

}