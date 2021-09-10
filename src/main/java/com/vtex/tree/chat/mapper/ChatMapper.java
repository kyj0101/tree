package com.vtex.tree.chat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface ChatMapper {
	
	List<MemberVO> getChatMemberList(String categoryNo) throws Exception;

	
}
