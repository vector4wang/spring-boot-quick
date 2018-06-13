package com.quick.mq.controller;


import com.quick.mq.model.Msg;
import com.quick.mq.scenes.delayTask.DelaySender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://blog.battcn.com/2018/05/23/springboot/v2-queue-rabbitmq-delay/
 */

@RestController
@RequestMapping("/delay")
@Api("延迟队列")
public class DelayController {

	@Autowired
	private DelaySender delaySender;


	@ApiOperation("延时队列发送(发送消息的时候设置过期时间)")
	@RequestMapping(value = "/sendDelay", method = RequestMethod.POST)
	public String sendDelayMsg(@RequestBody Msg msg) {

		delaySender.sendDelayMsg(msg);

		return "success";

	}

	@ApiOperation("延时队列发送(整个队列设置过期时间，与msg没有关系)")
	@RequestMapping(value = "/sendQueueDelay", method = RequestMethod.POST)
	public String sendDelayQueueMsg(@RequestBody Msg msg) {

		delaySender.sendDelayQueue(msg);

		return "success";

	}

}
