package com.vtex.tree.member.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.member.mapper.MemberMapper;

@Repository
public class MemberMapperImpl implements MemberMapper {
	
	@Autowired
	private SqlSession session;
}
