package com.quick.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
<<<<<<< HEAD
		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
=======
		SpringApplication.run(Application.class, args);
>>>>>>> origin/master
	}
}
