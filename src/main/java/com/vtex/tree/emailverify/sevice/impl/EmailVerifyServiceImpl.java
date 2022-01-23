package com.vtex.tree.emailverify.sevice.impl;

import com.vtex.tree.emailverify.mapper.EmailVerifyMapper;
import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.emailverify.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerifyServiceImpl implements EmailVerifyService {

    private final EmailVerifyMapper emailVerifyMapper;
    private final JavaMailSenderImpl mailSender;

    @Override
    public int updateEmailVerify(Map<String, String> param) {
        return emailVerifyMapper.updateEmailVerify(param);
    }

    @Override
    public void emailSend(Email email) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(Email.FROM);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText(), true);

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

    @Override
    public String makeEmailUrl(String ip,
                                String port,
                                String mapping,
                                String param) {
        return "https://" + ip + ":" +  port + mapping + param;
    }

}
