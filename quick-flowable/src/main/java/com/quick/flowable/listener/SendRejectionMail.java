package com.quick.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 *
 * @author wangxc
 * @date: 2022/7/23 22:54
 *
 */
@Slf4j
public class SendRejectionMail implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) {
		System.out.println(
				"execution.getCurrentFlowElement().getName() = " + execution.getCurrentFlowElement().getName());
		System.out.println("请求被驳回~");
	}
}
