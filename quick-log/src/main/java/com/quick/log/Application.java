package com.quick.log;

import com.quick.component.enables.QsEnableAroundLog;
import com.quick.component.enables.QsEnableGlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: B  MHJQS
 */
@SpringBootApplication
@EnableScheduling
@QsEnableGlobalExceptionHandler
@QsEnableAroundLog(value = "execution(public * com.*..service.*.*(..))")
public class Application {
    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
