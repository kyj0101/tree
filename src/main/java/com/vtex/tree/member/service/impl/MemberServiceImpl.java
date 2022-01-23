
package com.vtex.tree.member.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.member.mapper.MemberMapper;
import com.vtex.tree.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public List<String> getDepartmentList() {
		return memberMapper.getDepartmentList();
	}

	@Override
	public List<String> getPositionList() {
		return memberMapper.getPositionList();
	}

	@Override
	public void updateMember(Map<String, Object> param) {
		memberMapper.updateMember(param);
	}
	
	@Override
	public int withdraw(Map<String, Object> param) throws Exception {
		return memberMapper.withdraw(param);
	}

}
