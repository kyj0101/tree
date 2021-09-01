package com.vtex.tree.category.board.controller;

import java.util.ArrayList;
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
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.socket.handler.SocketHandler;

@ResponseBody
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/category/board")
@Controller
public class CategoryBoardController {
	
	@Autowired
	private CategoryBoardService categoryBoardService;
	
	/**
	 * 카테고리 insert
	 * @param emailList
	 * @param title
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	public String insertCategoryBoard(@RequestParam(value="esntlIdList[]") List<String> esntlIdList, 
										String title,
										String projectId,
										HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		esntlIdList.add(member.getEsntlId());

		int resultCnt = 0;
		int categoryNo = 0;
		
		Map<String,Object> param = new HashMap<>();
		
		param.put("title", title);
		param.put("loginEsntlId", member.getEsntlId());
		
		resultCnt = categoryBoardService.insertCategoryBoard(param);
		categoryNo = Integer.parseInt(param.get("no") + "");

		param.put("categoryNo", categoryNo);
		
		for(String esntlId : esntlIdList) {
			
			param.put("esntlId", esntlId);
			resultCnt = categoryBoardService.insertCategoryBoardUser(param);
		}
		
		if(resultCnt > 0) {
			return "ok";
			
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/delete")
	public String deleteCategoryBoard(String categoryNo, HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, Object> param = new HashMap<>();
		
		param.put("email", member.getEmail());
		param.put("categoryNo", categoryNo);
		
		int resultCnt = categoryBoardService.deleteCategoryBoard(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}
	
}
