package com.vtex.tree.category.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.annotation.LoginUser;

@ResponseBody
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/category")
@Controller
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
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
										@LoginUser Member member) {
		
		esntlIdList.add(member.getEsntlId());

		int resultCnt = 0;
		int categoryNo = 0;
		
		Map<String,Object> param = new HashMap<>();
		
		param.put("title", title);
		param.put("loginEsntlId", member.getEsntlId());
		param.put("projectId", projectId);
		
		resultCnt = categoryService.insertCategoryBoard(param);
		categoryNo = Integer.parseInt(param.get("no") + "");

		param.put("categoryNo", categoryNo);
		
		for(String esntlId : esntlIdList) {
			
			param.put("esntlId", esntlId);
			resultCnt = categoryService.insertCategoryBoardMember(param);
		}
		
		if(resultCnt > 0) {
			return "ok";
			
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/delete")
	public String deleteCategoryBoard(String categoryNo, @LoginUser Member member) throws Exception{

		Map<String, Object> param = new HashMap<>();
		
		param.put("loginEsntlId", member.getEsntlId());
		param.put("categoryNo", categoryNo);
		
		int resultCnt = categoryService.deleteCategoryBoard(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/out")
	public String outBoard(String categoryNo, @LoginUser Member member) throws Exception {
		return outBoard(categoryNo, member.getEsntlId(), member);
	}
	
	@RequestMapping("/out/manager")
	public String outBoard(String categoryNo, 
							String esntlId, 
							@LoginUser Member member) throws Exception {

		Map<String, Object> param = new HashMap<>();
		
		param.put("loginEsntlId", member.getEsntlId());
		param.put("categoryNo", categoryNo);
		param.put("esntlId", esntlId);
		
		int resultCnt = categoryService.outBoard(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@RequestMapping("/memberlist")
	public List<Member> getMemberListToInvite(String categoryNo) throws Exception {
		return categoryService.getMemberListToInvite(categoryNo);
	}
	
	
	@RequestMapping("/add/member")
	public String addMember(@RequestParam(value="esntlIdList[]") List<String> esntlIdList,
								String categoryNo,
								@LoginUser Member member) {

		int resultCnt = 0;
		Map<String, Object> param = new HashMap<>();
		
		param.put("categoryNo", categoryNo);
		param.put("loginEsntlId", member.getEsntlId());
		
		for(String esntlId : esntlIdList) {
		
			param.put("esntlId", esntlId);
			resultCnt += categoryService.insertCategoryBoardMember(param);
		}
		
		return resultCnt == esntlIdList.size() ? "ok" : "fail";
	}
	
}
