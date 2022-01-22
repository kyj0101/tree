package com.vtex.tree.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.common.enums.ChatType;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.security.annotation.LoginUser;
import com.vtex.tree.socket.handler.SocketHandler;


@RequestMapping("/chat")
@Controller
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

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
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("chatRoomNumber", category);
		param.put("email", member.getEmail());

		List<ChatVO> chatList = chatService.selectChatList(category);
		model.addObject("chatList", chatList);
		
		model.addObject("esntlId", member.getEsntlId());
		model.addObject("category", category);
		model.addObject("email", member.getEmail());
		model.addObject("name", member.getName());
		model.setViewName("chat/chat");

		return model;
	}
	

	@MessageMapping("/active/{category}")
	@SendTo("/chat/receive/topic/{category}")
	public ChatVO notice(@DestinationVariable String category, ChatVO chat) throws Exception {
		
		
		if(!ChatType.NOTICE.getType().equals(chat.getChatType())) {
			
			int result = chatService.insertChat(chat);
			
			List<MemberVO> memberList = getChatMemberList(category);
			SocketHandler socketHandler = new SocketHandler();

			socketHandler.sendChatMessage(memberList, chat);
		}

		return chat;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@RequestMapping("/member/list")
	public List<MemberVO> getChatMemberList(String categoryNo) throws Exception{
		
		List<MemberVO> memberList = chatService.getChatMemberList(categoryNo);
		
		return memberList;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String deleteChat(String chatRoomNumber) throws Exception {
		
		int result = chatService.deleteChat(chatRoomNumber);
		
		return result > 0 ? "ok" : "fail"; 
	}
}
