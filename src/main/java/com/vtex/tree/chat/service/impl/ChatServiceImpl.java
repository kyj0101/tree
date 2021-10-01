package com.vtex.tree.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.chat.mapper.ChatMapper;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatMapper chatMapper;

	@Override
	public int insertChat(ChatVO chat) throws Exception {
		return chatMapper.insertChat(chat);
	}
	
	@Override
	public List<MemberVO> getChatMemberList(String categoryNo) throws Exception {
		return chatMapper.getChatMemberList(categoryNo);
	}

	@Override
	public List<ChatVO> selectMyChatList(Map<String, Object> param) throws Exception {
		return chatMapper.selectMyChatList(param);
	}

	@Override
	public List<ChatVO> selectOtherChatList(Map<String, Object> param) throws Exception {
		return chatMapper.selectOtherChatList(param);
	}
	
	@Override
	public List<ChatVO> selectChatList(int category) throws Exception {
		return chatMapper.selectChatList(category);
	}
}