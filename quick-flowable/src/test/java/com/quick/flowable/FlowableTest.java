package com.quick.flowable;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wangxc
 * @date: 2022/7/21 23:03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FlowableApplication.class)
@Slf4j
public class FlowableTest {

	static ProcessEngine processEngine = null;


	/**
	 * 初始化StandaloneProcessEngineConfiguration
	 */
	@BeforeClass
	public static void initFactory() {
		log.info("init ProcessEngineConfiguration");
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl(
						"jdbc:mysql://127.0.0.1:3306/flowable-sample?characterEncoding=UTF-8").setJdbcUsername("root")
				.setJdbcPassword("123456").setJdbcDriver("com.mysql.cj.jdbc.Driver")
				//				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
		processEngine = cfg.buildProcessEngine();

	}
	//	@Test
	//	public void testInitTable() {
	//		ProcessEngine processEngine = cfg.buildProcessEngine();
	//	}

	@Test
	public void testInitProcess() {
		Deployment deploy = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("holiday-request.bpmn20.xml")
				.name(DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_FORMAT) + "_请假流程").category("holiday")
				.deploy();
		System.out.println("deploy.getId() = " + deploy.getId());
		System.out.println("deploy.getName() = " + deploy.getName());

	}

	@Test
	public void testDelProcess() {
		processEngine.getRepositoryService().deleteDeployment("25001", true);
	}

	@Test
	public void testQueryProcess() {
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId("32501").singleResult();
		System.out.println("Found process definition : " + processDefinition.getName());
		System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
		System.out.println("processDefinition.getName() = " + processDefinition.getName());
		System.out.println("processDefinition.getKey() = " + processDefinition.getKey());
		System.out.println("processDefinition.getId() = " + processDefinition.getId());

	}


	/**
	 * 初始化一个流程，通过variables完善第一个节点的表单信息
	 */
	@Test
	public void testStartAProcess() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("employee", "vector");
		variables.put("nrOfHolidays", "10");
		variables.put("description", "休育儿假");

		// 设置节点里的变量？
		variables.put("manager", "一星");
		ProcessInstance processInstance = runtimeService.startProcessInstanceById("holidayRequest:1:32503", variables);
		System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());

	}

	@Test
	public void testCompleteTask() {
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processDefinitionKey("holidayRequest").taskAssignee("一星")
				.singleResult();
		System.out.println("task.getDelegationState() = " + task.getDelegationState());
		System.out.println("task.getName() = " + task.getName());


		Map<String, Object> variables = new HashMap<>();
		variables.put("approved", false);
		taskService.complete(task.getId(), variables);

	}


	@Test
	public void testQueryProcessInstance() {
		String processId = "17501";
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
		System.out.println("pi.getProcessInstanceId() = " + pi.getProcessInstanceId());
	}



	@Test
	public void testQueryHistory() {
		HistoryService historyService = processEngine.getHistoryService();
		HistoricActivityInstanceQuery hi = historyService.createHistoricActivityInstanceQuery();
		List<HistoricActivityInstance> list = hi.processDefinitionId("holidayRequest:1:32503").finished() // 历史状态已完成
				.orderByHistoricActivityInstanceEndTime().asc().list();
		for (HistoricActivityInstance history : list) {
			System.out.println(history.getActivityName() + ": " + history.getAssignee() + "--"
					+ history.getActivityId()+": "+ history.getDurationInMillis());
		}


	}

}
