package com.quick.kafka;

import com.quick.kafka.service.simulation.SimulationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
		String userId = "20";

		// 1 2 3
		String pid = "3";

		/**
		 * 	  p_history和p_history的信息
		 *
		 * 	  消息体 用户id,产品id,timestamp,action
		 * 	 history 1,1,1563460819,view
		 *
		 * 	 con  1,1,1563459420431,view
		 */
		String format = String.format("%s,%s,%s,%s", userId, pid, new Date().getTime(), 1);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		simulationService.send("con", format);
		format = String.format("%s,%s,%s,%s", userId, pid, new Date().getTime(), 1);
		simulationService.send("history", format);


	}

}
