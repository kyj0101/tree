package com.vtex.tree.home.mapper;

import com.vtex.tree.emailverify.mapper.EmailVerifyMapper;
import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.member.vo.MemberVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HomeMapperTest {

    @Autowired
    private HomeMapper homeMapper;

    @Autowired
    private EmailVerifyMapper emailVerifyMapper;

    @Test
    void insertMember() {

        //given
        MemberVO member = new MemberVO("test", "test", "01011112222", "20000101", "123456789", "000", "000", "000", "test", "test", "001", "000", "test", "test");

        Map<String, String> param = new HashMap<>();
        param.put("email", member.getEmail());

        //when
        homeMapper.insertMember(member);
        emailVerifyMapper.updateEmailVerify(param);

        //then
        MemberVO savedMember = homeMapper.selectOneMember(member.getEmail());
        Assertions.assertThat(savedMember).isEqualTo(member);
    }
}