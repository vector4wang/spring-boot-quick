package com.quick.flowable;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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



	@Test
	public void testGetImg() throws IOException {
		String procInstId = "30001";
		// 通过流程实例ID获取历史流程实例
		HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
		// 通过流程实例ID获取流程中已经执行的节点，按照执行先后顺序排序
		List<HistoricActivityInstance> historicActivityInstanceList = processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(procInstId).orderByHistoricActivityInstanceId()
				.asc().list();
		// 将已经执行的节点ID放入高亮显示节点集合
		List<String> highLightedActivitiIdList = new ArrayList<>();
		for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
			highLightedActivitiIdList.add(historicActivityInstance.getActivityId());
			log.info("已执行的节点[{}-{}-{}-{}]", historicActivityInstance.getId(), historicActivityInstance.getActivityId(),
					historicActivityInstance.getActivityName(), historicActivityInstance.getAssignee());
		}
		// 通过流程实例ID获取流程中正在执行的节点
		List<Execution> runningActivityInstanceList = processEngine.getRuntimeService().createExecutionQuery()
				.processInstanceId(procInstId).list();
		List<String> runningActivitiIdList = new ArrayList<String>();
		for (Execution execution : runningActivityInstanceList) {
			if (StringUtils.isNotEmpty(execution.getActivityId())) {
				runningActivitiIdList.add(execution.getActivityId());
				log.info("执行中的节点[{}-{}-{}]", execution.getId(), execution.getActivityId(), execution.getName());
			}
		}
		// 通过流程实例ID获取已经完成的历史流程实例
		List<HistoricProcessInstance> historicFinishedProcessInstanceList = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId(procInstId).finished().list();
		// 定义流程画布生成器
		ProcessDiagramGenerator processDiagramGenerator = null;
		// 如果还没完成，流程图高亮颜色为绿色，如果已经完成为红色
		// if (!CollectionUtils.isEmpty(historicFinishedProcessInstanceList)) {
		// // 如果不为空，说明已经完成
		// processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
		// } else {
		processDiagramGenerator = new CustomProcessDiagramGenerator();
		// }
		// 获取流程定义Model对象
		BpmnModel bpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
		// 获取已流经的流程线，需要高亮显示高亮流程已发生流转的线id集合
		List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);
		// 使用默认配置获得流程图表生成器，并生成追踪图片字符流
		InputStream imageStream = ((CustomProcessDiagramGenerator) processDiagramGenerator).generateDiagramCustom(
				bpmnModel, "png", highLightedActivitiIdList, runningActivitiIdList, highLightedFlowIds, "宋体", "微软雅黑",
				"黑体", null, 2.0);
		// 将InputStream数据流转换为byte[]
		byte[] buffer = new byte[imageStream.available()];
		imageStream.read(buffer);
		final String suffix = ".png";
		File dest = new File(UUID.fastUUID() + "_" + procInstId + suffix);
		IOUtils.write(buffer, new FileOutputStream(dest));
	}

	/**
	 * 获取已流经的流程线，需要高亮显示高亮流程已发生流转的线id集合
	 *
	 * @param bpmnModel
	 * @param historicActivityInstanceList
	 * @return
	 */
	public List<String> getHighLightedFlows(BpmnModel bpmnModel,
			List<HistoricActivityInstance> historicActivityInstanceList) {
		// 已流经的流程线，需要高亮显示
		List<String> highLightedFlowIdList = new ArrayList<>();
		// 全部活动节点
		List<FlowNode> allHistoricActivityNodeList = new ArrayList<>();
		// 已完成的历史活动节点
		List<HistoricActivityInstance> finishedActivityInstanceList = new ArrayList<>();
		for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
			// 获取流程节点
			FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess()
					.getFlowElement(historicActivityInstance.getActivityId(), true);
			allHistoricActivityNodeList.add(flowNode);
			// 结束时间不为空，当前节点则已经完成
			if (historicActivityInstance.getEndTime() != null) {
				finishedActivityInstanceList.add(historicActivityInstance);
			}
		}
		FlowNode currentFlowNode = null;
		FlowNode targetFlowNode = null;
		HistoricActivityInstance currentActivityInstance;
		// 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
		for (int k = 0; k < finishedActivityInstanceList.size(); k++) {
			currentActivityInstance = finishedActivityInstanceList.get(k);
			// 获得当前活动对应的节点信息及outgoingFlows信息
			currentFlowNode = (FlowNode) bpmnModel.getMainProcess()
					.getFlowElement(currentActivityInstance.getActivityId(), true);
			// 当前节点的所有流出线
			List<SequenceFlow> outgoingFlowList = currentFlowNode.getOutgoingFlows();
			/**
			 * 遍历outgoingFlows并找到已流转的 满足如下条件认为已流转：
			 * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
			 * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
			 * (第2点有问题，有过驳回的，会只绘制驳回的流程线，通过走向下一级的流程线没有高亮显示)
			 */
			if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(
					currentActivityInstance.getActivityType())) {
				// 遍历历史活动节点，找到匹配流程目标节点的
				for (SequenceFlow outgoingFlow : outgoingFlowList) {
					// 获取当前节点流程线对应的下级节点
					targetFlowNode = (FlowNode) bpmnModel.getMainProcess()
							.getFlowElement(outgoingFlow.getTargetRef(), true);
					// 如果下级节点包含在所有历史节点中，则将当前节点的流出线高亮显示
					if (allHistoricActivityNodeList.contains(targetFlowNode)) {
						highLightedFlowIdList.add(outgoingFlow.getId());
					}
				}
			} else {
				/**
				 * 2、当前节点不是并行网关或兼容网关
				 * 【已解决-问题】如果当前节点有驳回功能，驳回到申请节点，
				 * 则因为申请节点在历史节点中，导致当前节点驳回到申请节点的流程线被高亮显示，但实际并没有进行驳回操作
				 */
				List<Map<String, Object>> tempMapList = new ArrayList<>();
				// 当前节点ID
				String currentActivityId = currentActivityInstance.getActivityId();
				int size = historicActivityInstanceList.size();
				boolean ifStartFind = false;
				boolean ifFinded = false;
				HistoricActivityInstance historicActivityInstance;
				// 循环当前节点的所有流出线
				// 循环所有历史节点
				log.info("【开始】-匹配当前节点-ActivityId=【{}】需要高亮显示的流出线", currentActivityId);
				log.info("循环历史节点");
				for (int i = 0; i < historicActivityInstanceList.size(); i++) {
					// // 如果当前节点流程线对应的下级节点在历史节点中，则该条流程线进行高亮显示（【问题】有驳回流程线时，即使没有进行驳回操作，因为申请节点在历史节点中，也会将驳回流程线高亮显示-_-||）
					// if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
					// Map<String, Object> map = new HashMap<>();
					// map.put("highLightedFlowId", sequenceFlow.getId());
					// map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
					// tempMapList.add(map);
					// // highLightedFlowIdList.add(sequenceFlow.getId());
					// }
					// 历史节点
					historicActivityInstance = historicActivityInstanceList.get(i);
					log.info("第【{}/{}】个历史节点-ActivityId=[{}]", i + 1, size, historicActivityInstance.getActivityId());
					// 如果循环历史节点中的id等于当前节点id，从当前历史节点继续先后查找是否有当前节点流程线等于的节点
					// 历史节点的序号需要大于等于已完成历史节点的序号，防止驳回重审一个节点经过两次是只取第一次的流出线高亮显示，第二次的不显示
					if (i >= k && historicActivityInstance.getActivityId().equals(currentActivityId)) {
						log.info("第[{}]个历史节点和当前节点一致-ActivityId=[{}]", i + 1, historicActivityInstance.getActivityId());
						ifStartFind = true;
						// 跳过当前节点继续查找下一个节点
						continue;
					}
					if (ifStartFind) {
						log.info("[开始]-循环当前节点-ActivityId=【{}】的所有流出线", currentActivityId);
						ifFinded = false;
						for (SequenceFlow sequenceFlow : outgoingFlowList) {
							// 如果当前节点流程线对应的下级节点在其后面的历史节点中，则该条流程线进行高亮显示
							// 【问题】
							log.info("当前流出线的下级节点=[{}]", sequenceFlow.getTargetRef());
							if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
								log.info("当前节点[{}]需高亮显示的流出线=[{}]", currentActivityId, sequenceFlow.getId());
								highLightedFlowIdList.add(sequenceFlow.getId());
								// 暂时默认找到离当前节点最近的下一级节点即退出循环，否则有多条流出线时将全部被高亮显示
								ifFinded = true;
								break;
							}
						}
						log.info("[完成]-循环当前节点-ActivityId=【{}】的所有流出线", currentActivityId);
					}
					if (ifFinded) {
						// 暂时默认找到离当前节点最近的下一级节点即退出历史节点循环，否则有多条流出线时将全部被高亮显示
						break;
					}
				}
				log.info("【完成】-匹配当前节点-ActivityId=【{}】需要高亮显示的流出线", currentActivityId);
				// if (!CollectionUtils.isEmpty(tempMapList)) {
				// // 遍历匹配的集合，取得开始时间最早的一个
				// long earliestStamp = 0L;
				// String highLightedFlowId = null;
				// for (Map<String, Object> map : tempMapList) {
				// long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
				// if (earliestStamp == 0 || earliestStamp <= highLightedFlowStartTime) {
				// highLightedFlowId = map.get("highLightedFlowId").toString();
				// earliestStamp = highLightedFlowStartTime;
				// }
				// }
				// highLightedFlowIdList.add(highLightedFlowId);
				// }
			}

		}
		return highLightedFlowIdList;
	}

}
