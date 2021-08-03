package com.vtex.tree.home.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private HomeMapper homeMapper;
	
	@Override
	public boolean emailDuplicationCheck(String email) {
		return homeMapper.emailDuplicationCheck(email);
	}

	@Override
	public void insertMember(MemberVO member) {
		homeMapper.insertMember(member);
	}

	@Override
	public int updateEmailVerify(Map<String, String> param) {
		return homeMapper.updateEmailVerify(param);
	}

	@Override
	public MemberVO selectOneMember(String email) {
		return homeMapper.selectOneMember(email);
	}
	
}