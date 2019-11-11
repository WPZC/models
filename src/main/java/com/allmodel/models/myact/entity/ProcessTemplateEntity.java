package com.allmodel.models.myact.entity;

import javax.persistence.*;

/**
 * @Description  
 * @Author  Wqy
 * @Date 2019-09-29 
 */

@Entity
@Table( name ="process_template" )
public class ProcessTemplateEntity  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Long id;

	/**
	 * UUID流程模型唯一标识
	 */
   	@Column(name = "process_uuid" )
	private String processUuid;

	/**
	 * 任务名称
	 */
   	@Column(name = "task_name" )
	private String taskName;

	/**
	 * 任务序号
	 */
   	@Column(name = "task_index" )
	private String taskIndex;

	/**
	 * 是否为判断语句
	 */
   	@Column(name = "is_judge" )
	private String isJudge;

	/**
	 * 权限等级
	 */
   	@Column(name = "permission_level" )
	private String permissionLevel;

	/**
	 * 跳回
	 */
   	@Column(name = "process_jump_back" )
	private String processJumpBack;

	/**
	 * 部门
	 */
	@Column(name = "sector" )
	private String sector;

	/**
	 * 功能名称
	 */
	@Column(name = "function" )
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
