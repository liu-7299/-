package com.fh.admin.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    /**
     * 发送简单文本邮件
     */
    public static void sendmail(String email,String personal,String title,String content){
        System.out.println(DateUtil.dateToString(new Date(),DateUtil.STRING_H_M_S)+" 发送邮件开始！");
        Properties prop = new Properties();
         prop.setProperty("mail.host", SystemCount.MAIL_HOST);
         prop.setProperty("mail.transport.protocol", "smtp");
         prop.setProperty("mail.smtp.auth", "true");
         //创建session
         Session session = Session.getInstance(prop);
         //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
         session.setDebug(true);
        Transport ts = null;
        try {
            //通过session得到transport对象
            ts = session.getTransport();
            //使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(SystemCount.MAIL_HOST, SystemCount.MAIL_DEFSULT_USER, SystemCount.MAIL_DEFSULT_PASSWORD);
            //创建邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(SystemCount.MAIL_DEFSULT_USER,SystemCount.MAIL_DEFSULT_PERSONAL,"UTF-8"));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email,personal,"UTF-8"));
            //邮件的标题
            message.setSubject(title);
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            try {
                //关
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(DateUtil.dateToString(new Date(),DateUtil.STRING_H_M_S)+" 发送邮件结束！");
    }

}
