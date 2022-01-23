package com.vtex.tree.emailverify.sevice;

import com.vtex.tree.emailverify.vo.Email;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailVerifyService {

    int updateEmailVerify(Map<String, String> param);

    void emailSend(Email email) throws MessagingException;

    boolean emailDuplicationCheck(String email);

    String makeEmailUrl(String ip, String port, String mapping, String param);

}
