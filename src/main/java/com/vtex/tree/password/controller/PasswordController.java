package com.vtex.tree.password.controller;

import com.vtex.tree.common.util.TemporailyPassword;
import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.emailverify.vo.Email;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.password.service.PasswordService;
import com.vtex.tree.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;
    private final EmailVerifyService emailVerifyService;

    private final PasswordEncoder passwordEncoder;

    @Value("${ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    private final String EMAIL_SUBJECT = "Tree 임시 비밀번호 입니다.";
    private final String EMAIL_TEXT = "<p>임시 비밀번호: tempoPwd </p><p>감사합니다.</p> <p>- Tree -</p>";

    @GetMapping()
    public String findPasswordView() {
        return "home/findPassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping()
    public void findPassword(@RequestParam String email) throws MessagingException {

        String tempoPwd = TemporailyPassword.getTemporailyPassword(8);
        Map<String, String> param = new HashMap<>();

        param.put("password", passwordEncoder.encode(tempoPwd));
        param.put("email", email);

        passwordService.updatePassword(param);

        Email passwordEmail = new Email(email, EMAIL_SUBJECT, EMAIL_TEXT.replace("tempoPwd", tempoPwd));

        emailVerifyService.emailSend(passwordEmail);
    }

    @GetMapping("/{email}")
    public String myPageUpdatePasswordView(@PathVariable String email) {
        return "mypage/updatePassword";
    }

    @PostMapping("/{email}")
    public String myPageUpdatePassword( @PathVariable String email,
                                        @RequestParam String password,
                                        @RequestParam String newPassword,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttribute,
                                        @LoginUser Member member) {
        try {

            HttpSession session = request.getSession();

            Map<String, String> param = new HashMap<>();
            String encryptedPassword =  passwordEncoder.encode(newPassword);

            param.put("password", encryptedPassword);
            param.put("email", member.getEmail());

            passwordService.updatePassword(param);
            member.setPassword(encryptedPassword);
            session.setAttribute("loginMember", member);
            redirectAttribute.addFlashAttribute("msg", "비밀번호가 변경되었습니다.");

            return "redirect:/member/mypage/update/password/view";

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
