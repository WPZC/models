package com.allmodel.models.mailserver.maintype.textandfile;


import com.allmodel.models.mailserver.maintype.MailEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author WQY
 * @Date 2019/11/7 13:51
 * @Version 1.0
 */
@Data
@ApiModel(value = "textAndFileMailEntity",description = "文本和文件邮件实体类")
public class TextAndFileMailEntity extends MailEntity {

    /**
     * 文件地址全路径
     */
    @ApiModelProperty(name = "fileUrl",value = "文件地址全路径",required = true,dataType = "String")
    private String fileUrl;

    /**
     * 文件后缀
     */
    @ApiModelProperty(name = "fileSuffix",value = "文件后缀",required = true,dataType = "String")
    private String fileSuffix;

}
