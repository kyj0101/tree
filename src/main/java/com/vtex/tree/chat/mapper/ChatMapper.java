package com.vtex.tree.chat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface ChatMapper {
	
	List<MemberVO> getChatMemberList(String categoryNo) throws Exception;

	int insertChat(ChatVO chat) throws Exception;

	List<ChatVO> selectMyChatList(Map<String, Object> param) throws Exception;

	List<ChatVO> selectOtherChatList(Map<String, Object> param) throws Exception;

	List<ChatVO> selectChatList(int category) throws Exception;

	
}
