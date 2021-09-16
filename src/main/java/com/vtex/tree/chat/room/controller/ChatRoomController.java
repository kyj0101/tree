package com.vtex.tree.chat.room.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.chat.room.service.ChatRoomService;
import com.vtex.tree.chat.service.ChatService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.socket.handler.SocketHandler;

@PreAuthorize("hasRole('ROLE_USER')")
@ResponseBody
@RequestMapping("/chatroom")
@Controller
public class ChatRoomController {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@RequestMapping("/insert")
	public String insertCategoryChat(@RequestParam(value="esntlIdList[]") List<String> esntlIdList, 
									String title,
									String projectId,
									HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		String loginEsntlId = member.getEsntlId(); 
				
		int resultCnt = 0;
		int categoryNo = 0;
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("title", title);
		param.put("projectId", projectId);
		param.put("loginEsntlId", loginEsntlId);
		
		resultCnt = chatRoomService.insertCategoryChat(param);
		categoryNo = Integer.parseInt(param.get("no") + "");
		
		param.put("categoryNo", categoryNo);

		esntlIdList.add(loginEsntlId);
		
		for(String esntlId : esntlIdList) {
			
			param.put("esntlId", esntlId);
			resultCnt = chatRoomService.insertCategoryChatUser(param);
		}
		
		if(resultCnt > 0) {
			return "ok";
			
		}else {
			return "fail";
		}
	}

	@RequestMapping("/out")
	public String outChatRoom(String category, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		return outChatRoomManager(category, member.getEsntlId(), request);
	}
	
	@RequestMapping("/out/manager")
	public String outChatRoomManager(String category, String esntlId, HttpServletRequest request) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("category", category);
		param.put("esntlId", esntlId);
		
		int result = chatRoomService.outChatRoom(param);
		
		return result > 0 ? "ok" : "fail";
	}
	
	@RequestMapping("/delete")
	public String deleteChat(String category, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("category", category);
		param.put("esntlId", member.getEsntlId());
		param.put("loginEsntlId", member.getEsntlId());
		
		int result = chatRoomService.deleteChatRoom(param);
		
		if(result > 0) {
			return "ok";
		}
		
		return "fail";
	}
	
	@RequestMapping("/memberlist")
	public List<MemberVO> getMemberListToInvite(String categoryNo) throws Exception {
		return chatRoomService.getMemberListToInvite(categoryNo);
	}
	
	@RequestMapping("/add/member")
	public String addMember(@RequestParam(value="esntlIdList[]") List<String> esntlIdList,
								HttpServletRequest request,
								String categoryNo) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		int resultCnt = 0;
		Map<String, Object> param = new HashMap<>();
		
		param.put("categoryNo", categoryNo);
		param.put("loginEsntlId", member.getEsntlId());
		
		for(String esntlId : esntlIdList) {
		
			param.put("esntlId", esntlId);
			resultCnt += chatRoomService.insertCategoryChatMember(param);
		}
		
		return resultCnt == esntlIdList.size() ? "ok" : "fail";
	}
}
