package com.vtex.tree.emailverify.sevice;

import com.vtex.tree.member.vo.MemberVO;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailVerifyService {

    int updateEmailVerify(Map<String, String> param);

    void emailVerifySend(MemberVO member) throws MessagingException;

    boolean emailDuplicationCheck(String email);

}
