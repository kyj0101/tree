package com.vtex.tree.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.category.chat.service.CategoryChatService;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/chat")
@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private CategoryBoardService categoryBoardService;
	
	@Autowired
	private CategoryChatService categoryChatService;
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	
	@RequestMapping("/room")
	public String getChatRoom(HttpServletRequest request,
								@AuthenticationPrincipal MemberVO member,
								Model model,
								@RequestParam(defaultValue = "1") int category) throws Exception {
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", member);
		
		//현재 카테고리
		String chatName = categoryChatService.getChatRoomName(category);
		model.addAttribute("chatName", chatName);
		model.addAttribute("category",category);
		
		//카테고리 리스트

		//1. 게시판
		List<Map<String, Object>> categoryBoardMapList = categoryBoardService.getCategoryList(member.getEmail());
		model.addAttribute("categoryBoardMapList", categoryBoardMapList);
		
		//2. 채팅
		List<Map<String, Object>> categoryChatMapList = categoryChatService.getChatRoomList(member.getEmail());
		model.addAttribute("categoryChatMapList", categoryChatMapList);;
		
		//출퇴근 여부
		boolean isIn = attendanceService.isIn(member.getEmail()) > 0;
		boolean isOut = attendanceService.isOut(member.getEmail()) > 0;
		
		model.addAttribute("isIn", isIn);
		model.addAttribute("isOut", isOut);
		model.addAttribute("emptyMsg", emptyMsg);
		model.addAttribute("email", member.getEmail());
		model.addAttribute("name", member.getName());

		return "chat/chat";
	}
	
	@MessageMapping("/active/{category}")
	@SendTo("/active/{category}")
	public String notice(@DestinationVariable String category, @RequestBody String msg) {
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/leave")
	public String deleteChatUser(String category, @AuthenticationPrincipal MemberVO member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("category", category);
		param.put("email", member.getEmail());
		
		int result = chatService.deleteChatUser(param);
		
		if(result > 0) {
			return "ok";
		}
		
		return "fail";
	}
	

}
