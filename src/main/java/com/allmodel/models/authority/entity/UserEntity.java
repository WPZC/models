package com.allmodel.models.authority.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description  
 * @Author  WQY
 * @Date 2019-11-05 
 */
@Data
@Entity
@Table ( name ="user" )
@ApiModel(value = "user",description = "用户对象user")
public class UserEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	@ApiModelProperty(hidden = true)
	private Long id;

	/**
	 * 用户名
	 */
   	@Column(name = "username" )
	@ApiModelProperty(value = "用户名",name = "username",required = true,dataType = "String")
	private String username;

	/**
	 * 密码
	 */
   	@Column(name = "password" )
	@ApiModelProperty(value = "密码",name = "password",required = true,dataType = "String")
	private String password;

	/**
	 * 保存时间
	 */
   	@Column(name = "savetime" )
	@ApiModelProperty(hidden = true)
	private Date savetime;

	/**
	 * 手机号
	 */
   	@Column(name = "iphone" )
	@ApiModelProperty(value = "手机号",name = "iphone")
	private String iphone;

	/**
	 * 权限,1是最大权限，2是最小权限
	 */
   	@Column(name = "authority" )
	@ApiModelProperty(value = "权限",name = "authority")
	private Long authority;

	/**
	 * 性别，1男，2女
	 */
   	@Column(name = "sex" )
	@ApiModelProperty(value = "性别",name = "sex")
	private String sex;

	/**
	 * 组织机构编号
	 */
   	@Column(name = "organization_num" )
	@ApiModelProperty(value = "组织机构标号",name = "organizationNum",required = true,dataType = "String")
	private String organizationNum;

	/**
	 * 组织机构名称
	 */
   	@Column(name = "organization_name" )
	@ApiModelProperty(value = "组织机构名称",name = "organizationName",required = true,dataType = "String")
	private String organizationName;

}
