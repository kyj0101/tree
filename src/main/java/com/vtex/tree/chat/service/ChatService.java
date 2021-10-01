package com.vtex.tree.chat.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.MemberVO;

public interface ChatService {
	
	int insertChat(ChatVO chat) throws Exception;
	
	List<MemberVO> getChatMemberList(String categoryNo) throws Exception;

	List<ChatVO> selectChatList(int category) throws Exception;

}
