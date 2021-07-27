package com.vtex.tree.home.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.home.model.service.HomeService;

@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/")
	public String home() {
		List<String> memberList = homeService.getMemberList();
		System.out.println(memberList.get(0));
		
		return "home";
		
	}
}
