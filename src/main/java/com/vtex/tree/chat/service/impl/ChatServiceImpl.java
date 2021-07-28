package com.vtex.tree.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.chat.mapper.ChatMapper;
import com.vtex.tree.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatMapper chatMapper;
	
}
