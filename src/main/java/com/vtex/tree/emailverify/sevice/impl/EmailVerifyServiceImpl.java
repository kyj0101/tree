package com.vtex.tree.emailverify.sevice.impl;

import com.vtex.tree.emailverify.mapper.EmailVerifyMapper;
import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerifyServiceImpl implements EmailVerifyService {

    private final EmailVerifyMapper emailVerifyMapper;
    private final JavaMailSenderImpl mailSender;

    @Value("${email.from}")
    private final String EMAIL_FROM;

    @Value("${email.subject}")
    private final String EMAIL_SUBJECT;

    @Value("${email.text}")
    private final String EMAIL_TEXT;

    @Value("${ip}")
    private final String IP;

    @Value("${server.port}")
    private final String PORT;

    @Override
    public int updateEmailVerify(Map<String, String> param) {
        return emailVerifyMapper.updateEmailVerify(param);
    }

    @Override
    public void emailVerifySend(MemberVO member) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(EMAIL_FROM);
            helper.setTo(member.getEmail());
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(EMAIL_TEXT.replace("url", makeEmailUrl(member.getEmillKey(), member.getEmail())),
                            true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw e;
        }
    }

    @Override
    public boolean emailDuplicationCheck(String email) {
        int result = emailVerifyMapper.emailDuplicationCheck(email);
        return result > 0;
    }

    private String makeEmailUrl(String key, String email) {
        return "https://" + IP + ":" +  PORT + "/email/verify" + "?key=" + key + "&email=" + email;
    }

}
