package com.allmodel.models.myact.entity;

import javax.persistence.*;

/**
 * @Description  
 * @Author  Wqy
 * @Date 2019-09-29 
 */

@Entity
@Table( name ="process_params" )
public class ProcessParamsEntity  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Long id;

	/**
	 * 流程模板标识
	 */
   	@Column(name = "process_uuid_index" )
	private String processUuidIndex;

	/**
	 * 流程任务序号
	 */
   	@Column(name = "process_task_index" )
	private String processTaskIndex;

	/**
	 * 当前任务参数
	 */
   	@Column(name = "task_index_parameters" )
	private String taskIndexParameters;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessUuidIndex() {
		return this.processUuidIndex;
	}

	public void setProcessUuidIndex(String processUuidIndex) {
		this.processUuidIndex = processUuidIndex;
	}

	public String getProcessTaskIndex() {
		return this.processTaskIndex;
	}

	public void setProcessTaskIndex(String processTaskIndex) {
		this.processTaskIndex = processTaskIndex;
	}

	public String getTaskIndexParameters() {
		return this.taskIndexParameters;
	}

	public void setTaskIndexParameters(String taskIndexParameters) {
		this.taskIndexParameters = taskIndexParameters;
	}

}
