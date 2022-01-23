package com.vtex.tree.home.controller;

import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.home.service.impl.HomeServiceImpl;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.security.service.SecurityDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerTest{

    @Autowired
    private HomeService homeService;

    @Autowired
    private EmailVerifyService emailVerifyService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @Transactional
    void signUpTest() {

        //given
        MemberVO member = new MemberVO("test", "test", "01011112222", "20000101", "123456789", "000", "000", "000", "test", "test", "001", "000", "test", "test");

        Map<String, String> param = new HashMap<>();
        param.put("email", member.getEmail());

        //when
        homeService.insertMember(member);
        emailVerifyService.updateEmailVerify(param);

        //then
        MemberVO savedMember = (MemberVO) userDetailsService.loadUserByUsername("test");
        Assertions.assertThat(savedMember).isEqualTo(member);
    }

}