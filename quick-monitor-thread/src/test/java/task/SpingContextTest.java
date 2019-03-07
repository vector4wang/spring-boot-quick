package task;

import com.monitor.thread.Application;
import com.monitor.thread.config.ApplicationContextProvider;
import com.monitor.thread.task.MoniotrTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =Application.class)
public class SpingContextTest {
 
 
 
    @Test
    public void show()throws Exception{
        MoniotrTask m1=   ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
        MoniotrTask m2=ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
        MoniotrTask m3=ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
        System.out.println(m1+" => "+m1.getSpecialService());
        System.out.println(m2+" => "+m2.getSpecialService());
        System.out.println(m3+" => "+m3.getSpecialService());
 
    }
 
 
}