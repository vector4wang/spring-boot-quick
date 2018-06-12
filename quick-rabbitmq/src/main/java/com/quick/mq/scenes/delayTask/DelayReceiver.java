package com.quick.mq.scenes.delayTask;

import com.quick.mq.config.RabbitConfig;
import com.quick.mq.model.Msg;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DelayReceiver {

	@RabbitListener(queues = {RabbitConfig.PROCESS_QUEUE})
	public void process(Msg msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间: " + sdf.format(new Date()) + " ---> msg：【" + msg + "]");
	}
}
