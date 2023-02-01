package com.quick.flowable.controller;

import cn.hutool.core.bean.BeanUtil;
import com.quick.flowable.common.ResponseData;
import com.quick.flowable.model.DeploymentVO;
import com.quick.flowable.service.handler.ProcessHandler;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 流程模板相关api
 * @author wangxc
 * @date: 2022/7/25 22:48
 *
 */
@RestController
@RequestMapping("/flowable")
public class FlowableProcessController {

	@Autowired
	private ProcessHandler processHandler;

	@RequestMapping("/deployProcess")
	public ResponseData deployByZip(String name, String category, String tenantId, MultipartFile file) {
		Deployment deployment = null;
		try (ZipInputStream zipIn = new ZipInputStream(file.getInputStream(), UTF_8)) {
			deployment = processHandler.deploy(name, tenantId, category, zipIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//忽略二进制文件（模板文件、模板图片）返回
		DeploymentVO deploymentVO = BeanUtil.copyProperties(deployment, DeploymentVO.class, "");
		return ResponseData.success(deploymentVO);
	}

}
