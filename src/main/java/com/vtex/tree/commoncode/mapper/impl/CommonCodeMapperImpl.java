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
	public void updateCommonCode(Map<String, String> param) {
		session.update("commonCode.updateCommonCode", param);
	}
}
