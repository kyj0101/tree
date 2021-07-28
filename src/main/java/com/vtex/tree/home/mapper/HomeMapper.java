package com.vtex.tree.home.mapper;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface HomeMapper {
	List<String> getMemberList();
	
	boolean emailDuplicationCheck(String email);

	void insertMember(MemberVO member);

	int updateEmailVerify(Map<String, String> param);

	MemberVO selectOneMember(String userName);
}
