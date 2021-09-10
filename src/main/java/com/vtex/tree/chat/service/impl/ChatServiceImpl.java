package com.vtex.tree.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.chat.mapper.ChatMapper;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	public List<MemberVO> getChatMemberList(String categoryNo) throws Exception {
		return chatMapper.getChatMemberList(categoryNo);
	}
	
}