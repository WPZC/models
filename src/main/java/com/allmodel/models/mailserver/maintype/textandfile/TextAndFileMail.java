package com.allmodel.models.mailserver.maintype.textandfile;

import com.allmodel.models.mailserver.maintype.Mail;
import com.allmodel.models.mailserver.maintype.MailOperation;
import com.allmodel.models.mailserver.maintype.text.TextMail;
import com.allmodel.models.mailserver.maintype.text.TextMailEntity;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;

/**
 * @Author WQY
 * @Date 2019/11/7 13:51
 * @Version 1.0
 */
public class TextAndFileMail extends Mail<TextAndFileMailEntity> implements MailOperation<TextAndFileMailEntity> {

    @Override
    public void createSimpleMail(TextAndFileMailEntity msg) throws Exception {

        //从父类中获取通用session
        Session session = super.getMailSession();
        //使用session获取本次的连接通道
        Transport ts = super.setMailConnect(session);
        //将本次的基本内容写入message
        Message message = super.setMessageTextType(session,msg);

        //添加节点内容
        //创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource(msg.getFileUrl()));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(msg.getMailTitle()+msg.getFileSuffix(),"gb2312","B"));//title+".docx"

        //准备正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(msg.getMailContent(),"text/html;charset=UTF-8");

        //设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);     // 如果有多个附件，可以创建多个多次添加
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        message.setContent(mm);
        message.setSentDate(new Date());
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }


    public static void main(String[] args){
        try {

            TextAndFileMail mail = new TextAndFileMail();
            TextAndFileMailEntity msg = new TextAndFileMailEntity();
            msg.setMailContent("<body style=\"margin: 50px;\">\n" +
                    "<table style=\"width: 800px;text-align: center;border-collapse: collapse;margin: 10px auto;\">\n" +
                    "     <tr>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">序号</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品名称</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品型号</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">订单时间</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品单价</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品数量</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">订单金额</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">日期</td>\n" +
                    "     </tr>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">序号</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品名称</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品型号</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">订单时间</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品单价</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">产品数量</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">订单金额</td>\n" +
                    "        <td style=\"border: 1px black solid;background-color: white;height: 20px;width: 100px\" colspan=\"1\" rowspan=\"1\" class=\"a\">日期</td>\n" +
                    "     </tr>\n" +
                    "</table>\n" +
                    "<img src=\"http://localhost:7560/44b2c013495409239d8ad6da9c58d109b2de49b2.jpg\">\n" +
                    "</body>");
            msg.setMailTitle("你好世界");
            msg.setTargetMailAddress("1272722954@qq.com");
            msg.setFileUrl("F:\\桌面\\table.html");
            msg.setFileSuffix(".html");
            mail.createSimpleMail(msg);


            TextMail mail1 = new TextMail();
            TextMailEntity msg1 = new TextMailEntity();

            msg1.setMailContent("hello world!");
            msg1.setMailTitle("你好世界");
            msg1.setTargetMailAddress("1272722954@qq.com");

            mail1.createSimpleMail(msg1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
