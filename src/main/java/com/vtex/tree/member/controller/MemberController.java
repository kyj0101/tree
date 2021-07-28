package com.vtex.tree.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.member.service.MemberService;

@RequestMapping("/member")
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/mypage/update")
	public String myPageUpdate() {
		return "mypage/update";
	}
	
	@RequestMapping("/mypage/update/password")
	public String myPageUpdatePassword() {
		return "mypage/updatePassword"; 
	}
}
