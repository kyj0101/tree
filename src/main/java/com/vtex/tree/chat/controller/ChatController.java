package com.vtex.tree.chat.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.board.service.BoardService;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.stomp.handler.SocketHandler;

@RequestMapping("/chat")
@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	
	@RequestMapping("/room")
	public String getChatRoom(HttpServletRequest request,
								@AuthenticationPrincipal MemberVO member,
								Model model,
								@RequestParam(defaultValue = "1") int category) throws Exception {
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", member);

		//카테고리 리스트
		List<Map<String, Object>> categoryMapList = boardService.getCategoryList(member.getEmail());
		model.addAttribute("categoryMapList", categoryMapList);
		
		//현재 카테고리 
		Map<String, Object> categoryMap = boardService.getCategory(category);
		model.addAttribute("categoryMap", categoryMap);
		
		
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
	
	@MessageMapping("/active")
	@SendTo("/active")
	public String notice(@RequestBody String msg) {
		//db저장 로직등이 가능
		System.out.println();
		return msg;
	}
	

}
