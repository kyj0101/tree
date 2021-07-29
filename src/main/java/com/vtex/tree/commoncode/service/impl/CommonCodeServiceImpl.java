package com.vtex.tree.commoncode.service.impl;

import java.util.List;
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

	@Override
	public int getTotalCommonCode() {
		return commonCodeMapper.getTotalCommonCode();
	}

	@Override
	public List<Map<String, String>> selectCommonCodeList(Map<String, Object> param) {
		return commonCodeMapper.selectCommonCodeList(param);
	}

	@Override
	public void updateCommonCode(Map<String, String> param) {
		commonCodeMapper.updateCommonCode(param);
	}
	
}
