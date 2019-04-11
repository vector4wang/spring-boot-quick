package mq;

import com.multi.rabbitmq.Application;
import com.multi.rabbitmq.mq.RabbitMqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author vector
 * @date: 2019/4/11 0011 11:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMqProducerTest {

    @Resource
    private RabbitMqProducer rabbitMqProducer;

    @Test
    public void firstSend() {
        for (int i = 0; i < 100; i++) {
            rabbitMqProducer.firstSend();
        }
    }

    @Test
    public void secondSend() {
        rabbitMqProducer.secondSend();
    }

}
