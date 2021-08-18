package com.vtex.tree.category.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.chat.mapper.CategoryChatMapper;
import com.vtex.tree.category.chat.service.CategoryChatService;

@Service
public class CategoryChatServiceImpl implements CategoryChatService{

	@Autowired
	private CategoryChatMapper categoryChatMapper;
	
	@Override
	public int insertCategoryChat(Map<String, Object> param) throws Exception {
		return categoryChatMapper.insertCategoryChat(param);
	}

	@Override
	public int insertCategoryChatUser(Map<String, Object> param) throws Exception {
		return categoryChatMapper.insertCategoryChatUser(param);
	}
	
	@Override
	public List<Map<String, Object>> getChatRoomList(String email) throws Exception {
		return categoryChatMapper.getCategoryList(email);
	}

	@Override
	public String getChatRoomName(int category) {
		return categoryChatMapper.getChatRoomName(category);
	}
}
