package com.allmodel.models.mailserver.maintype;

import javax.mail.internet.MimeMessage;

/**
 * @Author WQY
 * @Date 2019/11/7 11:38
 * @Version 1.0
 */
public interface MailOperation<T> {

    /**
     * 发送邮件(存文本)
     * @param msg
     * @return
     */
    void createSimpleMail(T msg) throws Exception;
}
