package com.quick.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/26
 * Time: 21:30
 * Description:
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }
}
