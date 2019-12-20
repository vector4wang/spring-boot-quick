package com.quick.druid;

import com.github.jsonzou.jmockdata.JMockData;
import com.quick.druid.sys.entity.User;
import com.quick.druid.sys.mapper.UserMapper;
import com.quick.druid.sys.service.IUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@SpringBootApplication
@MapperScan("com.quick.druid.*.mapper")
public class Application implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


    @Override
    public void run(String... args) throws Exception {
        User user = userService.getById(1);
        System.out.println(user.toString());

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            User anyData= JMockData.mockSimpleType(User.class);
            anyData.setId(null);
            userList.add(anyData);
        }
        userService.saveBatch(userList);

    }
}
