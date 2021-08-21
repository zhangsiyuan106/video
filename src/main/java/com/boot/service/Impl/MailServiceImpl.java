package com.boot.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author hsw
 * @date 2021/8/13
 */
@Repository
public class MailServiceImpl {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    public void sendVertifyCode(String to, String title, String content) {
        MimeMessage message=mailSender.createMimeMessage();

        MimeMessageHelper helper= null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from); //发送人
            helper.setTo(to);   //收件人
            helper.setSubject(title);  //邮件名
            helper.setText(content,true);   //邮件内容（验证码）
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
