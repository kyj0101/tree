package com.vtex.tree.category.chat.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface CategoryChatService {

	int insertCategoryChat(Map<String, Object> param) throws Exception;

	int insertCategoryChatUser(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> getChatRoomList(String email) throws Exception;

	Map<String, Object> getChatRoom(int category) throws Exception;

	int outChatRoom(Map<String, Object> param) throws Exception;

	int deleteChatRoom(Map<String, Object> param) throws Exception;

	List<MemberVO> getMemberListToInvite(String categoryNo) throws Exception;

	int insertCategoryChatMember(Map<String, Object> param) throws Exception;

}
