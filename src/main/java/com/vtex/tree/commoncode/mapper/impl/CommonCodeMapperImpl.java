package com.vtex.tree.commoncode.mapper.impl;

import java.util.Map;

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
}
