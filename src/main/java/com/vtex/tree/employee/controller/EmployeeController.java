package com.vtex.tree.employee.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtex.tree.employee.service.EmployeeService;
import com.vtex.tree.member.vo.MemberVO;

@RequestMapping("/employee")
@Controller
public class EmployeeController {
	
	private final int NUMPERPAGE = 5;
	@Autowired
	private EmployeeService employeeService;
	
	
	@RequestMapping("/list")
	public String getEmployeeList(Model model, 
									@RequestParam(defaultValue = "1")int cPage,
									HttpServletRequest request) throws Exception {
		
		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, NUMPERPAGE);
		
		int totalContents = employeeService.getMemberListCnt();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		model.addAttribute("pageBar", pageBar);
		
		List<MemberVO> memberList = employeeService.getMemberList(rowBounds);
		model.addAttribute("memberList", memberList);
		
		return "manager/employee";
	}
	
	@RequestMapping("/detail")
	public ResponseEntity<MemberVO> getMemberDetail(String email) throws Exception{
		
		MemberVO member = employeeService.getMemberDetail(email);
		HttpHeaders header = new HttpHeaders();
		
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<MemberVO>(member, header, HttpStatus.OK);
	}
}
