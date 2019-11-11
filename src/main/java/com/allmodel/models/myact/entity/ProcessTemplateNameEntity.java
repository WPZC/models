package com.allmodel.models.myact.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description  
 * @Author  zhuhaoyu
 * @Date 2019-09-30 
 */

@Entity
@Table( name ="process_template_name" )
public class ProcessTemplateNameEntity  {


	/**
	 * 唯一标识
	 */
	@Id
   	@Column(name = "process_uuid" )
	private String processUuid;

	/**
	 * 流程名称
	 */
   	@Column(name = "process_name" )
	private String processName;

	/**
	 * 创建日期
	 */
   	@Column(name = "process_create_date" )
	private Date processCreateDate;

	public String getProcessUuid() {
		return this.processUuid;
	}

	public void setProcessUuid(String processUuid) {
		this.processUuid = processUuid;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Date getProcessCreateDate() {
		return this.processCreateDate;
	}

	public void setProcessCreateDate(Date processCreateDate) {
		this.processCreateDate = processCreateDate;
	}

}
