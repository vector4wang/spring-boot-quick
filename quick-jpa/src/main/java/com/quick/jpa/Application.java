package com.quick.jpa;

import com.quick.jpa.entity.JpaTest;
import com.quick.jpa.repository.JpaTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@SpringBootApplication
public class Application {

    @Autowired
    private JpaTestRepository jpaTestRepository;

    @PostConstruct
    public void test() {
        JpaTest jpaTest = new JpaTest();
        jpaTest.setName("test");
        jpaTest.setAddTime(new Date());
        jpaTestRepository.save(jpaTest);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
