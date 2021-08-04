package com.vtex.tree.home.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.member.vo.MemberVO;

public interface HomeService {

	boolean emailDuplicationCheck(String email);

	void insertMember(MemberVO member);

	int updateEmailVerify(Map<String, String> param);

	MemberVO selectOneMember(String email);

}
