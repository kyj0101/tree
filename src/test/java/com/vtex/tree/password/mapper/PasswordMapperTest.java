package com.vtex.tree.password.mapper;

import com.vtex.tree.emailverify.mapper.EmailVerifyMapper;
import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.member.vo.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PasswordMapperTest {

    @Autowired
    private HomeMapper homeMapper;

    @Autowired
    private PasswordMapper passwordMapper;

    @Autowired
    private EmailVerifyMapper emailVerifyMapper;

    @Test
    void updatePassword() {

        //given
        Member member = new Member("test", "test", "01011112222", "20000101", "123456789", "000", "000", "000", "test", "test", "001", "000", "test", "test");
        String updatePassword = "1234";

        Map<String, String> param = new HashMap<>();
        param.put("password", updatePassword);
        param.put("email", member.getEmail());

        //when
        homeMapper.insertMember(member);
        emailVerifyMapper.updateEmailVerify(param);
        passwordMapper.updatePassword(param);

        Member updatedPasswordMember = homeMapper.selectOneMember(member.getEmail());

        //then
        Assertions.assertThat(updatedPasswordMember.getPassword()).isEqualTo(updatePassword);
    }

}