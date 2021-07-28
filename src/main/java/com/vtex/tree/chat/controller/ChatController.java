package com.vtex.tree.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.chat.service.ChatService;

@RequestMapping("/chat")
@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@RequestMapping("/room")
	public String getChatRoom() {
		return "chat/chat";
	}
	
}
