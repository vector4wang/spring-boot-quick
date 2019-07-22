package com.quick.kafka.service.simulation;

import com.quick.kafka.service.ProducerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2019/7/18 下午9:01
 *
 */
@Service
public class SimulationService {

	@Resource
	private ProducerService producerService;

	/**
	 * 只是测试，暂且不对body做验证，默认body一定不为空
	 * @param topic
	 * @param body
	 */
	public void send(String topic, String body) {
		System.out.println(body);
		producerService.sendMessage(topic,body);
	}
}
