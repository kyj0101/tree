package com.vtex.tree.chat.room.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.Member;

public interface ChatRoomService {

	int insertCategoryChat(Map<String, Object> param) throws Exception;

	int insertCategoryChatUser(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> getChatRoomList(String email) throws Exception;

	Map<String, Object> getChatRoom(int category) throws Exception;

	int outChatRoom(Map<String, Object> param) throws Exception;

	int deleteChatRoom(Map<String, Object> param) throws Exception;

	List<Member> getMemberListToInvite(String categoryNo) throws Exception;

	int insertCategoryChatMember(Map<String, Object> param) throws Exception;

}
