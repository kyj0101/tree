package com.vtex.tree.category.chat.service;

import java.util.List;
import java.util.Map;

public interface CategoryChatService {

	int insertCategoryChat(Map<String, Object> param) throws Exception;

	int insertCategoryChatUser(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> getChatRoomList(String email) throws Exception;

	String getChatRoomName(int category);

}
