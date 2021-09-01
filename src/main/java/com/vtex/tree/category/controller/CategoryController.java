package com.vtex.tree.category.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.socket.handler.SocketHandler;

@RequestMapping("/category")
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/memberlist")
	public void getmemberList(String projectId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		
		response.setContentType("text/html;charset=UTF-8");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("email", member.getEmail());
		
		List<MemberVO> memberList = categoryService.getMemberList(param);
		List<MemberVO> loginMemberList = new ArrayList<>();
		
		for(MemberVO m : memberList) {
			
			for(MemberVO loginMember : SocketHandler.loginMemberList) {
				
				if(m.getEmail().equals(loginMember.getEmail())){				
					m.setLoginAt("Y");
				}				
			}
			
			loginMemberList.add(m);
		}
		
		new Gson().toJson(loginMemberList, response.getWriter());
	}
}
