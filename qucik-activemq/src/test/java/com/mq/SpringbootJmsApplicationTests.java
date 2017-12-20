package com.mq;

import com.mq.service.Producer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsApplicationTests {  
      
    @Autowired
    private Producer producer;
      
    @Test
    public void contextLoads() throws InterruptedException {  
        Destination destination = new ActiveMQQueue("mytest.queue");
          
        for(int i=0; i<100; i++){
            System.out.println(i);
            producer.sendMessage(destination, i + "-myname is chhliu!!!");
        }  
    }  
  
}  