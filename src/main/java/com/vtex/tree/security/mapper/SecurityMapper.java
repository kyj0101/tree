package com.vtex.tree.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface SecurityMapper {

	public MemberVO login(String username);

}
