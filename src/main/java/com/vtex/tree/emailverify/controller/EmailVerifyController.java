package com.vtex.tree.emailverify.controller;

import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/emailVerify")
@RequiredArgsConstructor
public class EmailVerifyController {

    private final EmailVerifyService emailVerifyService;

    private final String EMAIL_VERIFY_SUCCESS_MSG = "이메일 인증이 완료되었습니다.";
    private final String EMAIL_VERIFY_FAIL_MSG = "이메일 인증을 실패했습니다.";


    @PreAuthorize("permitAll()")
    @GetMapping
    public String emailVerify(Model model, String key, String email) {

        model.addAttribute("key", key);
        model.addAttribute("email", email);

        return "home/emailVerify";
    }

    @PostMapping()
    public String emailVerify(String email, String key, RedirectAttributes redirectAttribute) {
        Map<String, String> param = new HashMap<>();
        param.put("email", email);
        param.put("key", key);

        try {
            int result = emailVerifyService.updateEmailVerify(param);

            if (result > 0) {
                redirectAttribute.addFlashAttribute("msg", EMAIL_VERIFY_SUCCESS_MSG);
                return "redirect:/login";
            }

            redirectAttribute.addFlashAttribute("msg", EMAIL_VERIFY_FAIL_MSG);
            return "redirect:/";

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @ResponseBody
    @GetMapping("/{email}")
    public String emailDuplicationCheck(@PathVariable("email") String email) {

        boolean isDuplicate = emailVerifyService.emailDuplicationCheck(email);

        return isDuplicate ? "true" : "false";
    }

}
