package com.vtex.tree.home.service;

import com.vtex.tree.member.vo.Member;

public interface HomeService {

	void insertMember(Member member);

	Member selectOneMember(String email);
}
