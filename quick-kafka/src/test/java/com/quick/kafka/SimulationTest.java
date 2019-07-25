package com.quick.kafka;

import com.quick.kafka.service.simulation.SimulationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2019/7/21 下午9:50
 *
 */
@SpringBootTest(classes = KafkaApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SimulationTest {

	@Resource
	private SimulationService simulationService;

	@Test
	public void testSimulation() {


		// 20 4 6 9 13 25
		String userId = "1";

		// 1 2 3
		String pid = "2";


		String action = "2";

		/**
		 * 	  p_history和p_history的信息
		 *
		 * 	  消息体 用户id,产品id,timestamp,action
		 * 	 history 1,1,1563460819,view
		 *
		 * 	 con  1,1,1563459420431,view
		 *
		 * 	    1,123,1563799391,2
		 * 		1,123,1563799393,2
		 * 		1,124,1563799394,2
		 * 		1,122,1563799398,2
		 * 		1,122,1563799412,1
		 * 		1,122,1563799418,1
		 * 		1,112,1563799419,1
		 * 		1,112,1563799420,1
		 * 		1,112,1563799428,1
		 */
		String format = String.format("%s,%s,%s,%s", userId, pid, new Date().getTime()/1000, action);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		simulationService.send("con", format);
		format = String.format("%s,%s,%s,%s", userId, pid, new Date().getTime()/1000, action);
		simulationService.send("history", format);


	}

}
