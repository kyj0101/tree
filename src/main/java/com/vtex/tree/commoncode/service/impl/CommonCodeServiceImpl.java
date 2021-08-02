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

	@Override
	public void insertDetailCode(Map<String, String> param) {
		commonCodeMapper.insertDetailCode(param);
	}

	@Override
	public List<Map<String, Object>> selectDetailCodeList(Map<String, Object> param) {
		return commonCodeMapper.selectDetailCodeList(param);
	}

	@Override
	public void deleteCommonCode(Map<String, String> param) {
		commonCodeMapper.deleteCommonCode(param);
	}

	@Override
	public int getTotalDetailCode(String code) {
		return commonCodeMapper.getTotalDetailCode(code);
	}

	@Override
	public Map<String, Object> selectOneDetailCode(String detailCode) {
		return commonCodeMapper.selectOneDetailCode(detailCode);
	}

	@Override
	public void updateDetailCode(Map<String, String> param) {
		commonCodeMapper.updateDetaiLCode(param);
	}

	@Override
	public Map<String, String> selectOneCommonCode(String code) {
		return commonCodeMapper.selectOneCommonCode(code);
	}

	@Override
	public boolean detailCodeDuplicationCheck(String code) {
		return commonCodeMapper.detailCodeDuplicatioCheck(code);
	}

	@Override
	public void deleteDetailCode(Map<String, String> param) {
		commonCodeMapper.deleteDetailCode(param);
	}

	
	
}
