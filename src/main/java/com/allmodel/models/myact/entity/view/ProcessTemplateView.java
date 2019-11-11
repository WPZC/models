package com.allmodel.models.myact.entity.view;

import java.util.List;

/**
 * @Description  
 * @Author  Wqy
 * @Date 2019-09-29 
 */

public class ProcessTemplateView {


	private Long id;

	/**
	 * UUID流程模型唯一标识
	 */
	private String processUuid;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 任务序号
	 */
	private String taskIndex;

	/**
	 * 是否为判断语句
	 */
	private String isJudge;

	/**
	 * 权限等级
	 */
	private String permissionLevel;

	/**
	 * 跳回
	 */
	private String processJumpBack;

	/**
	 * 字段块
	 */
	private List<ProcessTemplateParamsFour> fields;

	/**
	 * 部门
	 */
	private String sector;

	/**
	 * 方法
	 */
	private String function;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessUuid() {
		return this.processUuid;
	}

	public void setProcessUuid(String processUuid) {
		this.processUuid = processUuid;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskIndex() {
		return this.taskIndex;
	}

	public void setTaskIndex(String taskIndex) {
		this.taskIndex = taskIndex;
	}

	public String getIsJudge() {
		return this.isJudge;
	}

	public void setIsJudge(String isJudge) {
		this.isJudge = isJudge;
	}

	public String getPermissionLevel() {
		return this.permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getProcessJumpBack() {
		return this.processJumpBack;
	}

	public void setProcessJumpBack(String processJumpBack) {
		this.processJumpBack = processJumpBack;
	}

	public List<ProcessTemplateParamsFour> getFields() {
		return fields;
	}

	public void setFields(List<ProcessTemplateParamsFour> fields) {
		this.fields = fields;
	}


	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
