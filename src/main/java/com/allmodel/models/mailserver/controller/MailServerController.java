package com.allmodel.models.mailserver.controller;

import com.allmodel.models.mailserver.maintype.text.TextMail;
import com.allmodel.models.mailserver.maintype.text.TextMailEntity;
import com.allmodel.models.mailserver.maintype.textandfile.TextAndFileMail;
import com.allmodel.models.mailserver.maintype.textandfile.TextAndFileMailEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WQY
 * @Date 2019/11/7 14:38
 * @Version 1.0
 */
@Api(value = "邮件发送服务")
@RestController("/mail")
public class MailServerController {

    /**
     * 发送纯文本邮件
     * @param mailEntity
     * @return
     */
    @RequestMapping(value = "/sendTextMail",method = RequestMethod.POST)
    @ApiOperation(value = "发送纯文本邮件")
    public String sendTextMail(@ApiParam(name = "mailEntity",value = "传入json格式",required = true) TextMailEntity mailEntity){

        TextMail textMail = new TextMail();

        try {
            textMail.createSimpleMail(mailEntity);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送失败";
    }

    /**
     * 发送文本和附件邮件
     * @param textAndFileMailEntity
     * @return
     */
    @RequestMapping(value = "/sendTextAndFileMail",method = RequestMethod.POST)
    @ApiOperation(value = "发送文本和附件邮件")
    public String sendTextAndFileMail(@ApiParam(name = "mailEntity",value = "传入json格式",required = true) TextAndFileMailEntity textAndFileMailEntity){

        TextAndFileMail textMail = new TextAndFileMail();

        try {
            textMail.createSimpleMail(textAndFileMailEntity);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送失败";
    }

}
