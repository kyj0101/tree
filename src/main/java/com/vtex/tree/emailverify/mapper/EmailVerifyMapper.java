package com.vtex.tree.emailverify.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface EmailVerifyMapper {
    int updateEmailVerify(Map<String, String> param);

    int emailDuplicationCheck(String email);
}
