package com.vtex.tree.category.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.chat.mapper.CategoryChatMapper;
import com.vtex.tree.category.chat.service.CategoryChatService;
import com.vtex.tree.member.vo.MemberVO;

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
		return categoryChatMapper.insertCategoryChatMember(param);
	}
	
	@Override
	public List<Map<String, Object>> getChatRoomList(String email) throws Exception {
		return categoryChatMapper.getCategoryList(email);
	}

	@Override
	public Map<String, Object> getChatRoom(int category) throws Exception {
		return categoryChatMapper.getChatRoom(category);
	}
	
	@Override
	public int outChatRoom(Map<String, Object> param) throws Exception {
		return categoryChatMapper.outChatRoom(param);
	}
	
	@Override
	public int deleteChatRoom(Map<String, Object> param) throws Exception {
		return categoryChatMapper.deleteChatRoom(param);
	}
	
	@Override
	public List<MemberVO> getMemberListToInvite(String categoryNo) throws Exception {
		return categoryChatMapper.getMemberListToInvite(categoryNo);
	}
	
	@Override
	public int insertCategoryChatMember(Map<String, Object> param) throws Exception {
		return categoryChatMapper.insertCategoryChatMember(param);
	}
}
