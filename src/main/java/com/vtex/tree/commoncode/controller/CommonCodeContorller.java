package com.vtex.tree.commoncode.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.member.vo.MemberVO;
@RequestMapping("/commoncode")
@Controller
public class CommonCodeContorller {
	
	private final int NUMPERPAGE = 5;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	//=================== 공통코드 =======================
	
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
	@RequestMapping("/code/insert")
	public String insertCommonCode(String codeName, 
									String code, 
									String useAt,
									HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");

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
	
	/**
	 * 코드 수정
	 * @param codeName
	 * @param code
	 * @param useAt
	 * @param request
	 * @return
	 */
	@RequestMapping("/code/update")
	public String updateCommonCode(String codeName, 
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
			commonCodeService.updateCommonCode(param);
			return "redirect:/commoncode/list";
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 공통코드 삭제
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("/code/delete")
	public String deleteCommonCode(String code, HttpServletRequest request) {
		
		try {
			HttpSession session = request.getSession();
			MemberVO member = (MemberVO) session.getAttribute("loginMember");
			Map<String, String> param = new HashMap<>();
			
			param.put("email", member.getEmail());
			param.put("code", code);
			
			commonCodeService.deleteCommonCode(param);
			
			return "redirect:/commoncode/list";
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	//=================== 공통 상세 코드 =======================
	
	/**
	 * 
	 * @param code
	 * @param detailCodeName
	 * @param detailCode
	 * @param detailUseAt
	 * @param request
	 * @return
	 */
	@RequestMapping("/detail/code/insert")
	public String insertDetailCode(String code,
									String detailCodeName,
									String detailCode, 
									String detailUseAt, 
									HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, String> param = new HashMap<>();
		
		param.put("code", code);
		param.put("detailCodeName", detailCodeName);
		param.put("detailCode", detailCode);
		param.put("detailUseAt", detailUseAt);
		param.put("email", member.getEmail());

		try {
			commonCodeService.insertDetailCode(param);
			return "redirect:/commoncode/list";

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/detail/code/list")
	public void getDetailCodeList(String code,
									@RequestParam(defaultValue="1")int cPage,
									HttpServletResponse response) throws IOException {
		
		try {
			
			Map<String, Object> param = new HashMap<>();
			param.put("numPerPage", NUMPERPAGE);
			param.put("cPage", cPage);
			param.put("code", code);
			
			List<Map<String, Object>> detailCodeMapList = commonCodeService.selectDetailCodeList(param);
			Integer totalNum = commonCodeService.getTotalDetailCode(code);
			Map<String, Object> totalMap = new HashMap<>();
			
			totalMap.put("total", totalNum);
			detailCodeMapList.add(totalMap);
 			
			System.out.println("===============" + detailCodeMapList.toString());
			response.setContentType("text/html;charset=UTF-8");
			new Gson().toJson(detailCodeMapList,response.getWriter());
		
		} catch (JsonIOException e) {
			throw e;
			
		} catch (IOException e) {
			throw e;
		}
	}
	

	
}
