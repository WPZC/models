package com.allmodel.models.mailserver.maintype.text;

import com.allmodel.models.mailserver.maintype.Mail;
import com.allmodel.models.mailserver.maintype.MailOperation;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author WQY
 * @Date 2019/11/7 11:06
 * @Version 1.0
 */
public class TextMail extends Mail<TextMailEntity> implements MailOperation<TextMailEntity> {


    @Override
    public void createSimpleMail(TextMailEntity msg) throws Exception {

        //从父类中获取通用session
        Session session = super.getMailSession();
        //使用session获取本次的连接通道
        Transport ts = super.setMailConnect(session);
        //将本次的内容写入message
        Message message = super.setMessageTextType(session,msg);
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        //关闭通道
        ts.close();
    }

    public static void main(String[] args){
        try {

            TextMail mail = new TextMail();
            TextMailEntity msg = new TextMailEntity();

            msg.setMailContent("hello world!");
            msg.setMailTitle("你好世界");
            msg.setTargetMailAddress("1272722954@qq.com");

            mail.createSimpleMail(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
