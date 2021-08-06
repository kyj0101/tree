package com.vtex.tree.commoncode.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	// =================== 공통코드 =======================

	/**
	 * 리스트 조회
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String getCommonCodeList(@RequestParam(defaultValue = "1") int cPage, Model model,
			HttpServletRequest request) {

		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", NUMPERPAGE);
		param.put("cPage", cPage);

		int offset = (cPage - 1) * NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, NUMPERPAGE);
		
		int totalContents = commonCodeService.getTotalCommonCode();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		List<Map<String, String>> commonCodeListMap = commonCodeService.selectCommonCodeList(param, rowBounds);

		model.addAttribute("commonCodeListMap", commonCodeListMap);
		model.addAttribute("pageBar", pageBar);

		return "manager/commonCode";
	}

	/**
	 * 코드 insert
	 * 
	 * @param codeName
	 * @param code
	 * @param useAt
	 * @param request
	 * @return
	 */
	@RequestMapping("/code/insert")
	public ModelAndView insertCommonCode(String codeName, String code, String useAt
			, HttpServletRequest request, Model model) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");

		Map<String, String> param = new HashMap<>();

		param.put("codeName", codeName);
		param.put("code", code);
		param.put("email", member.getEmail());
		param.put("useAt", useAt);
		System.out.println(useAt);
		String resultMsg = "";
		int resultCnt = commonCodeService.insertCommonCode(param);
		
		if(resultCnt > 0) {
			
			resultMsg = "ok";
			mav.addObject("resultCode", 0);
			mav.addObject("resultMsg", resultMsg);
		
		}else {
			resultMsg = "fail";
			mav.addObject("resultCode", -1);
			mav.addObject("resultMsg", resultMsg);
		}
		
		mav.setViewName("jsonView");
		return mav;
	}

	/**
	 * 코드 중복 검사
	 * 
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/code/duplication/check")
	public String codeDuplicationCheck(String code) {

		int isDuplicate = commonCodeService.codeDuplicationCheck(code);

		return isDuplicate > 0 ? "true" : "false";
	}

	/**
	 * 코드 수정
	 * 
	 * @param codeName
	 * @param code
	 * @param useAt
	 * @param request
	 * @return
	 */
	@RequestMapping("/code/update")
	public ModelAndView updateCommonCode(String codeName, 
											String code, 
											String useAt, 
											HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");

		Map<String, String> param = new HashMap<>();

		param.put("codeName", codeName);
		param.put("code", code);
		param.put("useAt", useAt);
		param.put("email", member.getEmail());

		int resultCnt = commonCodeService.updateCommonCode(param);
		String resultMsg = "ok";
		
		if(resultCnt > 0) {
			
			mav.addObject("resultCode", 0);
			mav.addObject("resultMsg", resultMsg);
		
		}else {
			resultMsg = "fail";
			mav.addObject("resultCode", -1);
			mav.addObject("resultMsg", resultMsg);
		}
		
		mav.setViewName("jsonView");
		return mav;
	}

	/**
	 * 공통코드 삭제
	 * 
	 * @param code
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/code/delete")
	public ModelAndView deleteCommonCode(String code, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, String> param = new HashMap<>();

		param.put("email", member.getEmail());
		param.put("code", code);

		int resultCnt = commonCodeService.deleteCommonCode(param);
		String resultMsg = "ok";
		
		if(resultCnt > 0) {
			
			mav.addObject("resultCode", 0);
			mav.addObject("resultMsg", resultMsg);
		
		}else {
			resultMsg = "fail";
			mav.addObject("resultCode", -1);
			mav.addObject("resultMsg", resultMsg);
		}
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	/**
	 * 공통 코드 상세보기
	 * @param code
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/code/detail")
	public void selectOneCommonCode(String code, HttpServletResponse response) {
		
		try {

			Map<String, String> commonCodeMap = commonCodeService.selectOneCommonCode(code);
			System.out.println(commonCodeMap);
			response.setContentType("text/html;charset=UTF-8");
			new Gson().toJson(commonCodeMap, response.getWriter());
		
		} catch (JsonIOException e) {
			throw e;
			
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	// =================== 공통 상세 코드 =======================

	/**
	 * 상세 공통 코드 리스트
	 * 
	 * @param code
	 * @param cPage
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail/code/list")
	public void getDetailCodeList(String code, @RequestParam(defaultValue = "1") int cPage,
			HttpServletResponse response) throws IOException {

		try {

			Map<String, Object> param = new HashMap<>();
			param.put("numPerPage", NUMPERPAGE);
			param.put("cPage", cPage);
			param.put("code", code);
			
			int offset = (cPage - 1) * NUMPERPAGE;		
			RowBounds rowBounds = new RowBounds(offset, NUMPERPAGE);
			
			List<Map<String, Object>> detailCodeMapList = commonCodeService.selectDetailCode(param,rowBounds);
			Integer totalNum = commonCodeService.getTotalDetailCode(code);
			Map<String, Object> totalMap = new HashMap<>();

			totalMap.put("total", totalNum);
			detailCodeMapList.add(totalMap);

			response.setContentType("text/html;charset=UTF-8");
			new Gson().toJson(detailCodeMapList, response.getWriter());

		} catch (JsonIOException e) {
			throw e;

		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 공통 상세코드 상세보기
	 * @param detailCode
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/detail/code/detail")
	public void selectOneDetailCode(String detailCode, HttpServletResponse response) {

		try {

			Map<String, Object> detailCodeMap = commonCodeService.selectOneDetailCode(detailCode);

			response.setContentType("text/html;charset=UTF-8");
			new Gson().toJson(detailCodeMap, response.getWriter());

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 공통 상세 코드 업데이트
	 * @param commonCode
	 * @param detailCode
	 * @param detailCodeName
	 * @param sortOrdr
	 * @param detailCodeUseAt
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping("/detail/code/update")
	public ResponseEntity<String> updateDetailCode(String commonCode, 
													String detailCode, 
													String detailCodeName, 
													String sortOrdr,
													String detailCodeUseAt,
													HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, String> param = new HashMap<>();

		param.put("email", member.getEmail());
		param.put("commonCode", commonCode);
		param.put("detailCode", detailCode);
		param.put("detailCodeName", detailCodeName);
		param.put("sortOrdr", sortOrdr);
		param.put("detailCodeUseAt", detailCodeUseAt);

		int resultCnt = commonCodeService.updateDetailCode(param);
		
		if(resultCnt > 0) {
			return new ResponseEntity<>("ok",HttpStatus.OK);
		
		}else {
			return new ResponseEntity<>("fail",HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 공통 상세 코드 중복검사
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail/code/duplication/check")
	public String detailCodeDuplicationCheck(String code) {

		int isDuplicate = commonCodeService.detailCodeDuplicationCheck(code);

		return isDuplicate > 0 ? "true" : "false";
	}
	
	/**
	 * 공통 상세 코드 insert
	 * @param commonCode
	 * @param detailCode
	 * @param detailCodeName
	 * @param sortOrdr
	 * @param detailCodeUseAt
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping("/detail/code/insert")
	public ResponseEntity<String> insertDetailCode(String commonCode, 
													String detailCode, 
													String detailCodeName, 
													String sortOrdr,
													String detailCodeUseAt,
													HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, String> param = new HashMap<>();
		
		param.put("email", member.getEmail());
		param.put("commonCode", commonCode);
		param.put("detailCode", detailCode);
		param.put("detailCodeName", detailCodeName);
		param.put("sortOrdr", sortOrdr);
		param.put("detailCodeUseAt", detailCodeUseAt);
		
		int resultCnt = commonCodeService.insertDetailCode(param);
		
		if(resultCnt > 0) {
			return new ResponseEntity<>("ok",HttpStatus.OK);
		
		}else {
			return new ResponseEntity<>("fail",HttpStatus.BAD_REQUEST);
		}
	}
	

	@RequestMapping("/detail/code/delete")
	public ResponseEntity<String> deleteDetailCode(String detailCode, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, String> param = new HashMap<>();

		param.put("email", member.getEmail());
		param.put("detailCode", detailCode);
		
		int resultCnt = commonCodeService.deleteDetailCode(param);
		
		if(resultCnt > 0) {
			return new ResponseEntity<>("ok",HttpStatus.OK);
		
		}else {
			return new ResponseEntity<>("fail",HttpStatus.BAD_REQUEST);
		}
	}

}
