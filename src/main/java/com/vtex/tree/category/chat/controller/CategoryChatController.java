package com.vtex.tree.category.chat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.vtex.tree.category.chat.service.CategoryChatService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.stomp.handler.SocketHandler;

@PreAuthorize("hasRole('ROLE_USER')")
@ResponseBody
@RequestMapping("/category/chat")
@Controller
public class CategoryChatController {
	
	@Autowired
	private CategoryChatService categoryChatService;
	
	@RequestMapping("/memberlist")
	public void getmemberList(HttpServletResponse response) throws JsonIOException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		new Gson().toJson(SocketHandler.loginMemberList, response.getWriter());
	}
	
	@RequestMapping("/insert")
	public String insertCategoryChat(@RequestParam(value="emailArr[]") List<String> emailArr, 
									String title,
									HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		int resultCnt = 0;
		int categoryNo = 0;
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("title", title);
		param.put("email", member.getEmail());
		
		resultCnt = categoryChatService.insertCategoryChat(param);
		categoryNo = Integer.parseInt(param.get("no") + "");
		
		param.put("categoryNo", categoryNo);
		
		for(String email : emailArr) {
			param.put("userEmail", email);
			resultCnt = categoryChatService.insertCategoryChatUser(param);
		}
		
		if(resultCnt > 0) {
			return "ok";
			
		}else {
			return "fail";
		}
	}
}
