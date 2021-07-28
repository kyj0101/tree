package com.vtex.tree.home.mapper.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.member.vo.MemberVO;

@Repository
public class HomeMapperImpl implements HomeMapper {
	
	@Autowired
	private SqlSession session;

	@Override
	public List<String> getMemberList() {
		return session.selectList("sample.test");
	}

	@Override
	public boolean emailDuplicationCheck(String email) {
		
		int result = session.selectOne("signup.emailCheck", email);
		
		return result > 0;
	}

	@Override
	public void insertMember(MemberVO member) {
		session.insert("signup.insertMember", member);
	}

	@Override
	public int updateEmailVerify(Map<String, String> param) {
		return session.update("signup.updateEmailVerify", param);
	}

	@Override
	public MemberVO selectOneMember(String userName) {
		return session.selectOne("security.selectOneMember", userName);
	}
}
