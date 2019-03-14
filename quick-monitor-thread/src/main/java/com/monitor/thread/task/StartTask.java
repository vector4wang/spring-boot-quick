package com.monitor.thread.task;

import com.monitor.thread.config.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Order(value = 999999)
public class StartTask {

	final static Logger logger = LoggerFactory.getLogger(StartTask.class);



	//定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init() {
        MoniotrTask moniotrTask = ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
        moniotrTask.start();
	}


}