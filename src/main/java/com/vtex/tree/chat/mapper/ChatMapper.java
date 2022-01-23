package com.vtex.tree.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.Member;

@Mapper
public interface ChatMapper {
	
	List<Member> getChatMemberList(String categoryNo) throws Exception;

	int insertChat(ChatVO chat) throws Exception;

	List<ChatVO> selectChatList(int category) throws Exception;

	int deleteChat(String chatRoomNumber) throws Exception;

	
}
