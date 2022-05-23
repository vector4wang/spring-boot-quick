package com.quick.crypt.test;


import cn.hutool.core.util.RandomUtil;
import com.quick.crypt.test.base.BeanCriteria;
import com.quick.crypt.test.entity.User;
import com.quick.crypt.test.service.UserService;
import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

@SpringBootTest(classes = CryptApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class DbTest {

    @Resource
    private UserService userService;

    @Test
    public void testInsert() {
//        User vector = User.builder().name("vector").phone("13333333333").build();
//        LongStream.range(0, 10).forEach(k -> {
//            System.out.println(k);
        User vector = new User();
        vector.setName("vector" + 1);
        vector.setPhone("13333333333");
        log.info("wait crypt: {}", vector);
        User insert = userService.insert(vector);
        log.info("insert obj {}", insert);
//        });
    }

    @Test
    public void testUpdate() {
        BeanCriteria beanCriteria = new BeanCriteria(User.class);
        beanCriteria.createCriteria().andEqualTo("id", 153);

        User updateUser = new User();
        updateUser.setPhone("17727826853");

        userService.updateByExampleSelective(updateUser, beanCriteria);

    }

    @Test
    public void testInsertStr() {
        userService.insert("vector", "11111111111");
    }

    @Test
    public void testBatchInsert() {
//        User vector = User.builder().name("vector").phone("13333333333").build();
        List<User> list = new ArrayList<>();
        LongStream.range(0, 10).forEach(k -> {
            System.out.println(k);
            User vector = new User();
            vector.setName("vector" + k);
            vector.setPhone("13333333333");

            list.add(vector);
        });
        int sum = userService.batchInsert(list);
        log.info("insert obj {}", sum);
    }

    @Test
    public void testBatchSetInsert() {
//        User vector = User.builder().name("vector").phone("13333333333").build();
        Set<User> sets = new HashSet<>();
        LongStream.range(0, 10).forEach(k -> {
            System.out.println(k);
            User vector = new User();
            vector.setName("vector" + k);
            vector.setPhone(RandomUtil.randomNumbers(11));

            sets.add(vector);
        });
        int sum = userService.batchSetInsert(sets);
        log.info("insert obj {}", sum);
    }

    @Test
    public void testQueryById() {
        User user = userService.queryById(164);
        log.info("query: {}", user);
    }

    @Test
    public void testQueryAll() {
        List<User> userList = userService.findAll();
        log.info("query: {}", userList);
    }

    @Test
    public void testDeencrypt() throws NoSuchAlgorithmException {
        /**
         * 98929429633--->8720d9eb197889fe7761ed03dc455ea5
         */

        AesDesDefaultEncrypt aesDesDefaultEncrypt = new AesDesDefaultEncrypt("123");
        System.out.println(aesDesDefaultEncrypt.decrypt("0e22e227f48d13e17baa500f68e72024"));
    }
}