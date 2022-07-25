package com.quick.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 *
 * @author wangxc
 * @date: 2022/7/23 23:44
 *
 */
@Slf4j
public class ForgetFightListener implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		log.info(delegateTask.getOwner());
		log.info(delegateTask.getAssignee());
		log.info(delegateTask.getEventName());
	}
}
