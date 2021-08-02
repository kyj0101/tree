package com.vtex.tree.commoncode.mapper.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.commoncode.mapper.CommonCodeMapper;

@Repository
public class CommonCodeMapperImpl implements CommonCodeMapper{
	
	@Autowired
	private SqlSession session;
	
	// ========================== 공통 코드 ================================= 
	@Override
	public void insertCommonCode(Map<String, String> param) {
		session.insert("commonCode.insertCommonCode", param);
	}

	@Override
	public boolean codeDuplicationCheck(String code) {
		
		int result = session.selectOne("commonCode.codeDuplicationCheck", code); 
		
		return result > 0;
	}

	@Override
	public int getTotalCommonCode() {
		return session.selectOne("commonCode.getTotalCommonCode");
	}

	@Override
	public List<Map<String, String>> selectCommonCodeList(Map<String, Object> param) {
		
		int cPage = (int)param.get("cPage");
		int limit = (int)param.get("numPerPage");
		int offset = (cPage - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return session.selectList("commonCode.selectCommonCodeList", param, rowBounds);
	}
	
	@Override
	public Map<String, String> selectOneCommonCode(String code) {
		return session.selectOne("commonCode.selectOneCommonCode", code);
	}

	@Override
	public void updateCommonCode(Map<String, String> param) {
		session.update("commonCode.updateCommonCode", param);
	}
	
	@Override
	public void deleteCommonCode(Map<String, String> param) {
		session.update("commonCode.deleteCommonCode", param);
	}
	
	// ========================== 공통 상세 코드 =================================
	
	@Override
	public void insertDetailCode(Map<String, String> param) {
		session.insert("commonCode.insertDetailCode", param);
	}
	
	@Override
	public List<Map<String, Object>> selectDetailCodeList(Map<String, Object> param) {

		int cPage = (int)param.get("cPage");
		int limit = (int)param.get("numPerPage");
		int offset = (cPage - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return session.selectList("commonCode.selectDetailCode", param, rowBounds);
	}

	@Override
	public Map<String, Object> selectOneDetailCode(String detailCode) {
		return session.selectOne("commonCode.selectOneDetailCode", detailCode);
	}
	
	@Override
	public int getTotalDetailCode(String code) {
		return session.selectOne("commonCode.getTotalDetailCode", code);
	}

	@Override
	public void updateDetaiLCode(Map<String, String> param) {
		session.update("commonCode.updateDetailCode", param);
	}

	@Override
	public boolean detailCodeDuplicatioCheck(String code) {
		int result = session.selectOne("commonCode.detailCodeDuplicatioCheck", code); 
		
		return result > 0;
	}

	@Override
	public void deleteDetailCode(Map<String, String> param) {
		session.delete("commonCode.deleteDetailCode", param);
	}

	
}
