package com.quick.mulit;

import com.quick.mulit.datasource.DynamicDataSourceRegister;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@SpringBootApplication
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class Application {
}
