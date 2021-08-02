package com.vtex.tree.member.mapper.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.member.mapper.MemberMapper;

@Repository
public class MemberMapperImpl implements MemberMapper {
	
	@Autowired
	private SqlSession session;

	@Override
	public List<String> getDepartmentList() {
		return session.selectList("member.getDepartmentList");
	}

	@Override
	public List<String> getPositionList() {
		return session.selectList("member.getPositionList");
	}

	@Override
	public void updateMember(Map<String, Object> param) {
		session.update("member.updateMember", param);
	}
}
