package com.vtex.tree.password.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PasswordMapper {
    void updatePassword(Map<String, String> param);
}
