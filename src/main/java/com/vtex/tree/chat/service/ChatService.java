package com.vtex.tree.chat.service;

import java.util.List;

import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.Member;

public interface ChatService {
	
	int insertChat(ChatVO chat) throws Exception;
	
	List<Member> getChatMemberList(String categoryNo) throws Exception;

	List<ChatVO> selectChatList(int category) throws Exception;

	int deleteChat(String chatRoomNumber) throws Exception;

}
