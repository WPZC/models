package com.allmodel.models.authority.entity;

import com.allmodel.models.authority.AuthorityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @Description  
 * @Author  WQY
 * @Date 2019-11-05 
 */
@Data
@Entity
@Table ( name ="organization" )
@ApiModel(value = "organizationEntity",description = "组织机构")
public class OrganizationEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	@ApiModelProperty(hidden = true)
	private Long id;

	/**
	 * 组织机构名称
	 */
   	@Column(name = "organization_name" )
	@ApiModelProperty(value = "组织机构名称",name = "organizationName",required = true,dataType = "String")
	private String organizationName;

	/**
	 * 组织机构编号
	 */
   	@Column(name = "organization_serial_num" )
	@ApiModelProperty(hidden = true)
	private String organizationSerialNum;

	/**
	 * 紧急联系人
	 */
   	@Column(name = "contact_person_name" )
	@ApiModelProperty(value = "紧急联系人",name = "contactPersonName",required = true,dataType = "String")
	private String contactPersonName;

	/**
	 * 紧急联系人手机号
	 */
   	@Column(name = "contact_person_phone" )
	@ApiModelProperty(value = "紧急联系人手机号",name = "contactPersonPhone",required = true,dataType = "String")
	private String contactPersonPhone;

	/**
	 * 监管类型
	 */
   	@Column(name = "supervision_type" )
	@ApiModelProperty(value = "监管类型",name = "supervisionType",dataType = "String")
	private String supervisionType;


}
