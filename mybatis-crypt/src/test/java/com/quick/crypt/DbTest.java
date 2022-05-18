package com.quick.crypt;


import com.quick.db.crypt.CryptApplication;
import com.quick.db.crypt.entity.User;
import com.quick.db.crypt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = CryptApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class DbTest {

    @Resource
    private UserService userService;

    @Test
    public void testInsert() {
        User vector = User.builder().name("vector").phone("13333333333").build();
        User insert = userService.insert(vector);
        log.info("insert obj {}", insert);
    }

    @Test
    public void testQueryById() {
        User user = userService.queryById(1);
        log.info("query: {}", user);
    }
}