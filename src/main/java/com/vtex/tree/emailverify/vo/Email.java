package com.vtex.tree.emailverify.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Getter
@AllArgsConstructor
public class Email {

    private String to;
    private String subject;
    private String text;

    public static final String FROM = "tree";
}
