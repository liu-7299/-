package com.fh.admin.commons;

import com.fh.admin.po.User;
import com.fh.admin.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class Sendmail {

    public static void toQQSendmail(User user, String requestPath){
        String content="<h1>尊敬的"+user.getRealName()+"，您的账号在"+DateUtil.dateToString(new Date(),DateUtil.STRING_H_M_S) +"尝试登陆，密码连续错误三次，用户已锁定，登陆地点为"+requestPath+",如不是您本人操作，请更新密码</h1>";
        sendmail("警告！！！",content,user.getEmail());
    }

    public static void toQQSendmail(String newPassword,User user){
        String content="<h1>尊敬的"+user.getRealName()+"，您的新密码为"+newPassword+"</h1>";
        sendmail("更新密码成功《重要邮件》",content,user.getEmail());
    }

    public static void sendmail(String title,String content,String email){
        if(StringUtils.isEmpty(title)){
            title = "飞狐官方邮件";
        }
        if(StringUtils.isEmpty(content)){
            content = "";
        }
        if(StringUtils.isEmpty(email)){
            email = "729926610@qq.com";
        }
        try {
            System.out.println("开始发送邮件！");
            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.auth", true);

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("729926610@qq.com", "飞狐官方", "UTF-8"));//new InternetAddress("729926610@qq.com")
            message.setSubject(title);// 邮件标题

            Multipart mp = new MimeMultipart("related");
            BodyPart bodyPart = new MimeBodyPart();

            bodyPart.setDataHandler(new DataHandler(content,"text/html;charset=UTF-8"));

            mp.addBodyPart(bodyPart);
            message.setContent(mp);// 设置邮件内容对象

            message.setSentDate(new Date(System.currentTimeMillis()));
            message.saveChanges();
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.qq.com", "729926610@qq.com",
                    "bpaodpgzwqbvbccd");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送邮件结束！");
    }

}
