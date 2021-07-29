package com.vtex.tree.commoncode.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.commoncode.mapper.CommonCodeMapper;
import com.vtex.tree.commoncode.service.CommonCodeService;


@Service
public class CommonCodeServiceImpl implements CommonCodeService{

	@Autowired
	private CommonCodeMapper commonCodeMapper;

	@Override
	public void insertCommonCode(Map<String, String> param) {
		commonCodeMapper.insertCommonCode(param);
	}

	@Override
	public boolean codeDuplicationCheck(String code) {
		return commonCodeMapper.codeDuplicationCheck(code);
	}
	
}
