package com.company.springforum.service.email;

import com.company.springforum.pojo.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Receiver receiver, Integer code, Template template) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(receiver.getEmailAddress());
        helper.setFrom("GuangDongSam@Gmail.com","MyForum");
        helper.setSubject("Thank you for your registration");
        helper.setText(template.loadTemplate(receiver.getUsername(),code),true);
        javaMailSender.send(msg);
    }


}
