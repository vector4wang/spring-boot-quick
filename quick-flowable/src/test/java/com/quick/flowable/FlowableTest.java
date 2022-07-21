package com.quick.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		processEngine = cfg.buildProcessEngine();

	}



//	@Test
//	public void testInitTable() {
//		ProcessEngine processEngine = cfg.buildProcessEngine();
//	}

	@Test
	public void testInitProcess() {
		Deployment deploy = processEngine.getRepositoryService()
				.createDeployment()
				.addClasspathResource("holiday-request.bpmn20.xml")
				.deploy();
		System.out.println("deploy.getId() = " + deploy.getId());

	}

	@Test
	public void testQueryProcess() {
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId("1")
				.singleResult();
		System.out.println("Found process definition : " + processDefinition.getName());

	}

	@Test
	public void testStartAProcess() {

		RuntimeService runtimeService = processEngine.getRuntimeService();
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("employee", "vector");
		variables.put("nrOfHolidays", "10");
		variables.put("description", "休育儿假");
		variables.put("employee",new ArrayList<String>(){{add("张三");
			add("李四");}});

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
		System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
		//		for (Method declaredMethod : processInstance.getClass().getDeclaredMethods()) {
//			if (!declaredMethod.getName().startsWith("get")) {
//				continue;
//			}
//			System.out.print(declaredMethod.getName());
//			try {
//				Object invoke = declaredMethod.invoke(processInstance);
//				System.out.println(" : " + invoke);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		}

	}

	@Test
	public void testQueryProcessInstanceStatus() {
		TaskService taskService = processEngine.getTaskService();
//		taskService.createTaskQuery().insta
	}



}
