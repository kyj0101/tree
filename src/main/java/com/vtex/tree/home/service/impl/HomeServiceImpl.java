package com.vtex.tree.home.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.vo.Member;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private HomeMapper homeMapper;

	@Override
	public void insertMember(Member member) {
		homeMapper.insertMember(member);
	}

	@Override
	public Member selectOneMember(String email) {
		return homeMapper.selectOneMember(email);
	}
}