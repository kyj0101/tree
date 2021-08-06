package com.vtex.tree.category.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.member.vo.MemberVO;

@ResponseBody
@Controller
@RequestMapping("/category/board")
public class CategoryBoardController {
	
	@Autowired
	private CategoryBoardService categoryBoardService;
	
	//카테고리에 선택할 직원목록 
	@RequestMapping("/memberlist")
	public void getMemberList(HttpServletResponse response) throws Exception{
		
		List<MemberVO> memberList = categoryBoardService.getMemberList();

		response.setContentType("text/html;charset=UTF-8");
		new Gson().toJson(memberList, response.getWriter());
	}
	
	/**
	 * 카테고리 insert
	 * @param emailArr
	 * @param title
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	public String insertCategoryBoard(@RequestParam(value="emailArr[]") List<String> emailArr, 
										String title,
										HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		int resultCnt = 0;
		int categoryNo = 0;
		
		Map<String,Object> param = new HashMap<>();
		
		param.put("title", title);
		param.put("email", member.getEmail());
		
		resultCnt = categoryBoardService.insertCategoryBoard(param);
		categoryNo = Integer.parseInt(param.get("no") + "");

		param.put("categoryNo", categoryNo);
		
		for(String email : emailArr) {
			param.put("userEmail", email);
			resultCnt = categoryBoardService.insertCategoryBoardUser(param);
		}
		
		if(resultCnt > 0) {
			return "ok";
			
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/delete")
	public String deleteCategoryBoard(String categoryNo) throws Exception{
		
		int resultCnt = categoryBoardService.deleteCategoryBoard(categoryNo);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}
	
}
