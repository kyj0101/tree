package com.vtex.tree.emailverify.mapper;

import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.member.vo.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Transactional
class EmailVerifyMapperTest {

    @Autowired
    private HomeMapper homeMapper;

    @Autowired
    private EmailVerifyMapper emailVerifyMapper;

    @Test
    void updateEmailVerify() {

        //given
        Member member = new Member("test", "test", "01011112222", "20000101", "123456789", "000", "000", "000", "test", "test", "001", "000", "test", "test");
        Map<String, String> param = new HashMap<>();

        //when
        homeMapper.insertMember(member);

        param.put("email", member.getEmail());
        param.put("key", member.getEmillKey());

        emailVerifyMapper.updateEmailVerify(param);

        //then
        Member emailVerifyMember = homeMapper.selectOneMember(member.getEmail());
        Assertions.assertThat(emailVerifyMember.getEmailVerifyPnttm()).isNotNull();
    }

    @Test
    void emailDuplicationCheck() {

        //given
        Member member = new Member("test", "test", "01011112222", "20000101", "123456789", "000", "000", "000", "test", "test", "001", "000", "test", "test");

        //when
        homeMapper.insertMember(member);

        //then
        Assertions.assertThat(emailVerifyMapper.emailDuplicationCheck(member.getEmail())).isGreaterThan(0);
    }

}