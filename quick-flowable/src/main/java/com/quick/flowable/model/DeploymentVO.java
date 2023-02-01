package com.quick.flowable.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2022/7/25 22:56
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String category;
	private String key;
	private String tenantId;
	private Date deploymentTime;
	private String derivedFrom;
	private String derivedFromRoot;
	private String parentDeploymentId;
	private String engineVersion;
	private Boolean deleted;
	private Boolean inserted;
	private String idPrefix;
	private Boolean updated;
}
