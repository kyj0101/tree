package com.vtex.tree.home.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.member.vo.MemberVO;

public interface HomeService {

	void insertMember(MemberVO member);

	MemberVO selectOneMember(String email);

	int setLogin(String email);

	int setLogout(String email);

}
