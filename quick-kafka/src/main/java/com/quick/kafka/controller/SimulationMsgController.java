package com.quick.kafka.controller;

import com.quick.kafka.service.simulation.SimulationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 这个是用来模拟生成实时推荐用户所产生的日志消息
 * @author wangxc
 * @date: 2019/7/18 下午8:59
 *
 */
@RestController
@RequestMapping("/rec")
public class SimulationMsgController {

	@Resource
	private SimulationService simulationService;

	/**
	 * p_history和p_history的信息
	 *
	 * 消息体 用户id,产品id,timestamp,action
	 * history 1,1,1563460819,view
	 *
	 *con  1,1,1563459420431,view
	 *
	 * @param topic
	 * @param body
	 * @return
	 */
	@PostMapping("/send/{topic}")
	public String send(@PathVariable("topic")String topic,@RequestBody String body) {
		simulationService.send(topic,body);
		return "send success";
	}

}
