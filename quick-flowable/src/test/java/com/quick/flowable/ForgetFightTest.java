package com.quick.flowable;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author wangxc
 * @date: 2022/7/21 23:03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FlowableApplication.class)
@Slf4j
public class ForgetFightTest {

	static ProcessEngine processEngine = null;


	/**
	 * 初始化StandalonePffflow.bpmn20.xmlrocessEngineConfiguration
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

	@Test
	public void testInitProcess() {
		Deployment deploy = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("ffflow.bpmn20.xml")
				.name(DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_FORMAT) + "_打卡流程").category("fight")
				.deploy();
		System.out.println("deploy.getId() = " + deploy.getId());
		System.out.println("deploy.getName() = " + deploy.getName());
		System.out.println("deploy.getKey() = " + deploy.getKey());

	}

	@Test
	public void testDelProcess() {
		processEngine.getRepositoryService().deleteDeployment("45001", true);
	}

	@Test
	public void testQueryProcess() {
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId("45001").singleResult();
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
		variables.put("datetime", "2022-07-24");
		variables.put("description", "忘记打卡");
		ProcessInstance processInstance = runtimeService.startProcessInstanceById("test-key:1:45004", variables);
		System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
		/**
		 * 自己提交的时候，应该自带一个处理过程
		 */
	}

	@Test
	public void testQueryWaitProcessTask() {
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processDefinitionKey("test-key")
//				.taskAssignee("$INITIATOR")
								.taskAssignee("bm")
				.singleResult();
		if (Objects.nonNull(task)) {
			System.out.println("task.getDelegationState() = " + task.getDelegationState());
			System.out.println("task.getName() = " + task.getName());
			System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId());
		}


	}

	@Test
	public void testProcessNode() {
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processDefinitionKey("test-key").taskAssignee("$INITIATOR")
				.singleResult();
		if (Objects.nonNull(task)) {
			System.out.println("task.getDelegationState() = " + task.getDelegationState());
			System.out.println("task.getName() = " + task.getName());

			// 个人审批
			Map<String, Object> variables = new HashMap<>();
			variables.put("manager", "bm");
			taskService.complete(task.getId(), variables);
			//			 领导审批
			//			taskService.complete(task.getId());
		}


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
			System.out.println(
					history.getActivityName() + ": " + history.getAssignee() + "--" + history.getActivityId() + ": "
							+ history.getDurationInMillis());
		}


	}


	@Test
	public void testGetImg() throws IOException {
		String processId = "57501";
		ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId)
				.singleResult();
		//流程走完的不显示图
		if (pi == null) {
			return;
		}
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		String InstanceId = task.getProcessInstanceId();
		List<Execution> executions = processEngine.getRuntimeService().createExecutionQuery()
				.processInstanceId(InstanceId).list();
		//得到正在执行的Activity的Id
		List<String> activityIds = new ArrayList<>();
		List<String> flows = new ArrayList<>();
		for (Execution exe : executions) {
			List<String> ids = processEngine.getRuntimeService().getActiveActivityIds(exe.getId());
			activityIds.addAll(ids);
		}
		//获取流程图
		String suffix = "png";
		BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(pi.getProcessDefinitionId());
		ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
		ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
		InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, suffix, activityIds, flows,
				engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
				engconf.getClassLoader(), 1.0, true);


		byte[] bytes = IOUtils.toByteArray(inputStream);
		IOUtils.write(bytes,new FileOutputStream(processId + "_" + UUID.randomUUID() + "." + suffix) );
	}
}