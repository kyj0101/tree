package com.vtex.tree.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.security.annotation.LoginUser;


@RequestMapping("/chat")
@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	/**
	 * 채팅방
	 * @param request
	 * @param model
	 * @param category
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/room")
	public ModelAndView getChatRoomView(@LoginUser MemberVO member,
								ModelAndView model,
								@RequestParam(defaultValue = "1") int category) throws Exception {
		
		model.addObject("esntlId", member.getEsntlId());
		model.addObject("category", category);
		model.addObject("email", member.getEmail());
		model.addObject("name", member.getName());
		model.setViewName("chat/chat");
		
		return model;
	}
	

	@MessageMapping("/active/{category}")
	@SendTo("/chat/receive/topic/{category}")
	public String notice(@DestinationVariable String category, String msg) {
		System.out.println("================================");
		System.out.println(msg);
		return msg;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@RequestMapping("/member/list")
	public List<MemberVO> getChatMemberList(String categoryNo) throws Exception{
		
		List<MemberVO> memberList = chatService.getChatMemberList(categoryNo);
		
		return memberList;
	}
}
