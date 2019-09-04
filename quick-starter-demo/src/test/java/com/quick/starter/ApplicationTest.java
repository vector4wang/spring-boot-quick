package com.quick.starter;

import com.quick.starter.autoconfigure.HelloService;
import com.quick.starter.demo.StarterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author vector
 * @date: 2019/8/6 0006 19:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterApplication.class)
public class ApplicationTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void testStarter() {
        System.out.println(helloService.hell());
    }
}
