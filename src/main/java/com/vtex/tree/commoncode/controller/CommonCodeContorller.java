package com.vtex.tree.commoncode.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.member.vo.MemberVO;
@RequestMapping("/commoncode")
@Controller
public class CommonCodeContorller {
	
	private final int NUMPERPAGE = 5;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	/**
	 * 리스트 조회
	 * @return
	 */
	@RequestMapping("/list")
	public String getCommonCodeList(@RequestParam(defaultValue="1")int cPage, 
									Model model,
									HttpServletRequest request){
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("numPerPage", NUMPERPAGE);
		param.put("cPage", cPage);
		
		int totalContents = commonCodeService.getTotalCommonCode();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		List<Map<String, String>> commonCodeListMap = commonCodeService.selectCommonCodeList(param);
		
		model.addAttribute("commonCodeListMap", commonCodeListMap);
		model.addAttribute("pageBar", pageBar);
		System.out.println(commonCodeListMap.toString());
		return "manager/commonCode";
	}
	
	/**
	 * 코드 insert 
	 * @param codeName
	 * @param code
	 * @param useAt
	 * @param request
	 * @return
	 */
	@RequestMapping("/list/insert")
	public String insertCommonCode(String codeName, 
									String code, 
									String useAt,
									HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		System.out.println(member.toString());
		Map<String, String> param = new HashMap<>();
		
		param.put("codeName", codeName);
		param.put("code", code);
		param.put("useAt", useAt);
		param.put("email", member.getEmail());
		
		
		try {
			commonCodeService.insertCommonCode(param);
			return "redirect:/commoncode/list";
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 코드 중복 검사
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/code/duplication/check")
	public String codeDuplicationCheck(String code) {
		
		boolean isDuplicate = commonCodeService.codeDuplicationCheck(code);
		
		return isDuplicate ? "true" : "false";
	}
	
}
