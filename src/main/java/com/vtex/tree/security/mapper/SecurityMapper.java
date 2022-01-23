package com.vtex.tree.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.Member;

@Mapper
public interface SecurityMapper {

	public Member login(String username);

}
