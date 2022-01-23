package com.vtex.tree.home.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.MemberVO;
@Mapper
public interface HomeMapper {



	void insertMember(MemberVO member);

	MemberVO selectOneMember(String userName);

	int setLogin(String email);

	int setLogout(String email);
}
