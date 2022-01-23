package com.vtex.tree.password.service.impl;

import com.vtex.tree.password.mapper.PasswordMapper;
import com.vtex.tree.password.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordMapper passwordMapper;

    @Override
    public void updatePassword(Map<String, String> param) {
        passwordMapper.updatePassword(param);
    }
}
