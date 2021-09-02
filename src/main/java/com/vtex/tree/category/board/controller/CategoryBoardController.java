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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
		param.put("projectId", projectId);
		
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
		
		param.put("loginEsntlId", member.getEsntlId());
		param.put("categoryNo", categoryNo);
		
		int resultCnt = categoryBoardService.deleteCategoryBoard(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/out")
	public String outBoard(String categoryNo, @AuthenticationPrincipal MemberVO member) throws Exception {
		return outBoard(categoryNo, member.getEsntlId(), member);
	}
	
	@RequestMapping("/out/manager")
	public String outBoard(String categoryNo, String esntlId, @AuthenticationPrincipal MemberVO member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginEsntlId", member.getEsntlId());
		param.put("categoryNo", categoryNo);
		param.put("esntlId", esntlId);
		
		int resultCnt = categoryBoardService.outBoard(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
}
