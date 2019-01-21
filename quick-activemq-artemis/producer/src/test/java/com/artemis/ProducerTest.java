package com.artemis;

import com.artemis.service.ArtemisProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author vector
 * @date: 2019/1/18 0018 14:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private ArtemisProducer artemisProducer;


    @Test
    public void testSend() {
        for (int i = 0; i < 10; i++) {
            artemisProducer.send("i am " + i +" hey!");
        }
    }
}
