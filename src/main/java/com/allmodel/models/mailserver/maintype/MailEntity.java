package com.allmodel.models.mailserver.maintype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author WQY
 * @Date 2019/11/7 13:58
 * @Version 1.0
 */
@Data
@ApiModel(value = "mail")
public class MailEntity {

    /**
     * 目标邮箱地址
     */
    @ApiModelProperty(value = "目标邮箱地址",name = "targetMailAddress",required = true,dataType = "String")
    private String targetMailAddress;
    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题",name = "mailTitle",required = true,dataType = "String")
    private String mailTitle;
    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容",name = "mailContent",required = true,dataType = "String")
    private String mailContent;
}
