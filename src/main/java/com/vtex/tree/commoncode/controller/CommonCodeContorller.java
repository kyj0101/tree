package com.vtex.tree.commoncode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.commoncode.service.CommonCodeService;

@RequestMapping("/commoncode")
@Controller
public class CommonCodeContorller {
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@RequestMapping("/list")
	public String getCommonCodeList() {
		return "manager/commonCode";
	}
	
}
