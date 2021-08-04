package com.vtex.tree.commoncode.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.commoncode.mapper.CommonCodeMapper;
import com.vtex.tree.commoncode.service.CommonCodeService;


@Service
public class CommonCodeServiceImpl implements CommonCodeService{

	@Autowired
	private CommonCodeMapper commonCodeMapper;

	@Override
	public int insertCommonCode(Map<String, String> param) throws Exception {
		return commonCodeMapper.insertCommonCode(param);
	}

	@Override
	public int codeDuplicationCheck(String code) {
		return commonCodeMapper.codeDuplicationCheck(code);
	}

	@Override
	public int getTotalCommonCode() {
		return commonCodeMapper.getTotalCommonCode();
	}

	@Override
	public List<Map<String, String>> selectCommonCodeList(Map<String, Object> param, RowBounds rowBounds) {		
		return commonCodeMapper.selectCommonCodeList(param, rowBounds);
	}

	@Override
	public int updateCommonCode(Map<String, String> param) throws Exception{
		return commonCodeMapper.updateCommonCode(param);
	}

	@Override
	public int insertDetailCode(Map<String, String> param) throws Exception {
		return commonCodeMapper.insertDetailCode(param);
	}

	@Override
	public List<Map<String, Object>> selectDetailCode(Map<String, Object> param , RowBounds rowBounds) {
		return commonCodeMapper.selectDetailCode(param, rowBounds);
	}

	@Override
	public int deleteCommonCode(Map<String, String> param) throws Exception{
		return commonCodeMapper.deleteCommonCode(param);
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
	public int updateDetailCode(Map<String, String> param) {
		return commonCodeMapper.updateDetailCode(param);
	}

	@Override
	public Map<String, String> selectOneCommonCode(String code) {
		return commonCodeMapper.selectOneCommonCode(code);
	}

	@Override
	public int detailCodeDuplicationCheck(String code) {
		return commonCodeMapper.detailCodeDuplicatioCheck(code);
	}

	@Override
	public int deleteDetailCode(Map<String, String> param) throws Exception {
		return commonCodeMapper.deleteDetailCode(param);
	}
	
	@Override
	public List<Map<String, String>> selectCmmnCodeList(Map<String, String> param) throws Exception {
		return commonCodeMapper.selectCmmnCodeList(param);
	}

	
	
}
