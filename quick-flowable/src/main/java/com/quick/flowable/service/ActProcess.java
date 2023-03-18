package com.quick.flowable.service;

import org.flowable.engine.repository.*;
import org.springframework.lang.Nullable;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义封装
 */
public interface ActProcess {
    /**
     * 创建流程部署对象
     *
     * @return
     */
    public DeploymentBuilder createDeployment();

    /**
     * 创建流程部署查询对象
     *
     * @return
     */
    public DeploymentQuery createDeploymentQuery();

    /**
     * 创建流程定义查询对象
     *
     * @return
     */
    public ProcessDefinitionQuery createProcessDefinitionQuery();

    /**
     * 部署流程定义
     *
     * @param url 流程定义文件URL
     * @return
     */
    Deployment deploy(String url);

    /**
     * 部署流程定义---通过inputstream流
     *
     * @param name     流程模板文件名字
     * @param tenantId 业务系统标识
     * @param category 流程模板文件类别
     * @param in       流程模板文件流
     * @return
     */
    Deployment deploy(String name, @Nullable String tenantId, @Nullable String category, InputStream in);

    /**
     * 部署流程定义
     *
     * @param url    流程定义文件URL
     * @param pngUrl 流程定义文件pngUrl
     * @return
     */
    Deployment deploy(String url, String pngUrl);

    /**
     * 部署压缩包内的流程资源—
     * 资源包括：bpmn、png、drl、form等等
     * 流程引擎：内部使用迭代器方式遍历压缩包中文件并读取成响应的文件流
     *
     * @param zipInputStream
     * @param name           流程模板文件名字
     * @param tenantId       业务系统标识
     * @param category       流程模板文件类别
     * @return
     */
    Deployment deploy(String name, String tenantId, String category, ZipInputStream zipInputStream);

    /**
     * 部署流程定义
     *
     * @param url    流程定义文件URL
     * @param drlUrl 规则引擎文件URL
     * @return
     */
    Deployment deployBpmnAndDrl(String url, String drlUrl);

    /**
     * 部署流程定义
     *
     * @param url      流程定义文件URL
     * @param name     流程定义名称
     * @param category 流程定义类别
     * @return
     */
    Deployment deploy(String url, String name, String category);

    /**
     * 部署流程定义
     *
     * @param url      流程定义文件URL
     * @param pngUrl   流程定义文件pngUrl
     * @param name     流程定义标识
     * @param category 流程定义类别
     * @return
     */
    Deployment deploy(String url, String pngUrl, String name, String category);

    /**
     * 根据流程定义key，判断流程定义（模板）是否已经部署过
     *
     * @param processDefinitionKey 流程定义key（即：流程模板ID）
     * @return
     */
    boolean exist(String processDefinitionKey);

    /**
     * 根据流程定义key，查询流程定义信息
     *
     * @param processDefinitionKey 流程定义key（即：流程模板ID）
     * @return
     */
    public ProcessDefinition queryByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 根据流程部署name，查询流程部署信息（最新）
     *
     * @param deploymentName 流程部署name
     * @return
     */
    Deployment deployName(String deploymentName);

    /**
     * 给流程定义授权用户
     *
     * @param processDefinitionKey 流程定义key
     * @param userId               流程定义key
     * @throws
     */
    void addCandidateStarterUser(String processDefinitionKey, String userId);
}
