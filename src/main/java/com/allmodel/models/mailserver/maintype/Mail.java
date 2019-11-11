package com.allmodel.models.mailserver.maintype;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.Data;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * @Author WQY
 * @Date 2019/11/7 11:05
 * @Version 1.0
 */
@Data
public abstract class Mail<T extends MailEntity> {

    private final String version = "1.0";

    /**
     * 获取mail的session
     * @return
     * @throws Exception
     */
    protected Session getMailSession() throws Exception{
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        return session;
    }

    /**
     * 设置邮件连接
     * @param session
     * @return
     * @throws Exception
     */
    protected Transport setMailConnect(Session session) throws Exception{
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect("smtp.qq.com","1272722954", "yjqxvfyxeslsjfbg");//后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）

        return ts;
    }

    protected Message setMessageTextType(Session session,T msg) throws Exception{
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("1272722954@qq.com"));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        //615555648@qq.com
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(msg.getTargetMailAddress()));
        // 邮件的标题
        message.setSubject(msg.getMailTitle());
        // 邮件的文本内容
        message.setContent(msg.getMailContent(), "text/html;charset=UTF-8");

        return message;
    }
}
