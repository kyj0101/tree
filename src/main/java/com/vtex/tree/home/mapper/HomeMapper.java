package com.vtex.tree.home.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.Member;
@Mapper
public interface HomeMapper {

	void insertMember(Member member);

	Member selectOneMember(String userName);
}
