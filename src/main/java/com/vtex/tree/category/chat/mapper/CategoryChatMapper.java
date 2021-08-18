package com.vtex.tree.category.chat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryChatMapper {

	int insertCategoryChat(Map<String, Object> param) throws Exception;

	int insertCategoryChatUser(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> getCategoryList(String email) throws Exception;
	
	String getChatRoomName(int category);
}
