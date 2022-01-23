package com.vtex.tree.space.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.annotation.LoginUser;
import com.vtex.tree.socket.handler.SocketHandler;
import com.vtex.tree.space.service.SpaceService;

@ResponseBody
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/space")
@Controller
@RequiredArgsConstructor
public class SpaceController {

	private final SpaceService spaceService;
	
	
	@RequestMapping("/memberlist")
	public void getMemberList(String projectId, HttpServletResponse response, @LoginUser Member member) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("email", member.getEmail());
		
		List<Member> memberList = spaceService.getMemberList(param);
		List<Member> loginMemberList = new ArrayList<>();
		
		for(Member m : memberList) {
			
			for(Member loginMember : SocketHandler.loginMemberList) {
				
				if(m.getEmail().equals(loginMember.getEmail())){				
					m.setLoginAt("Y");
				}				
			}
			
			loginMemberList.add(m);
		}
		
		new Gson().toJson(loginMemberList, response.getWriter());
	}
	
	@RequestMapping("/onlinememberlist")
	public List<Member> getOnlineMemberList(@LoginUser Member member, HttpServletResponse response) throws JsonIOException, IOException {
		
		List<Member> loginMemberList = SocketHandler.loginMemberList;
		List<Member> cloneList = loginMemberList.stream().collect(Collectors.toList());
		
		cloneList.remove(member);
		
		return cloneList;
	}
	
}
