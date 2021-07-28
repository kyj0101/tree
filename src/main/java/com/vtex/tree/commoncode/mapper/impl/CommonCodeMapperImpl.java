package com.vtex.tree.commoncode.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.commoncode.mapper.CommonCodeMapper;

@Repository
public class CommonCodeMapperImpl implements CommonCodeMapper{
	
	@Autowired
	private SqlSession session;
}
