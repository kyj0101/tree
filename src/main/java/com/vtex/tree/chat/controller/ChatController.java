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
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.service.ChatRoomService;
import com.vtex.tree.chat.room.vo.ChatRoomVO;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.security.annotation.LoginUser;

@PreAuthorize("hasRole('ROLE_USER')")
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
	@SendTo("/active/{category}")
	public String notice(@DestinationVariable String category, @RequestBody String msg) {
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/member/list")
	public List<MemberVO> getChatMemberList(String categoryNo) throws Exception{
		
		List<MemberVO> memberList = chatService.getChatMemberList(categoryNo);
		
		return memberList;
	}
}
