package com.vtex.tree.category.chat.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.stomp.handler.SocketHandler;

@PreAuthorize("hasRole('ROLE_USER')")
@ResponseBody
@RequestMapping("/category/chat")
@Controller
public class CategoryChatController {
	

	@RequestMapping("/memberlist")
	public void getmemberList(HttpServletResponse response) throws JsonIOException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		new Gson().toJson(SocketHandler.loginMemberList, response.getWriter());
	}
}
