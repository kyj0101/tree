package com.vtex.tree.employee.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.employee.service.EmployeeService;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.annotation.LoginUser;
import static com.vtex.tree.common.util.PageBar.getOffset;

@RequestMapping("/employee")
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class EmployeeController {

	private final int NUMPERPAGE = 5;

	private final EmployeeService employeeService;
	private final CommonCodeService commonCodeService;
	
	@Value("${empty.msg}")
	private String emptyMsg;

	@RequestMapping("/list")
	public String getEmployeeList(Model model, 
									@RequestParam(defaultValue = "1")int cPage,
									HttpServletRequest request) throws Exception {
		

		RowBounds rowBounds = new RowBounds(getOffset(cPage, NUMPERPAGE), NUMPERPAGE);		
		int totalContents = employeeService.getMemberListCnt();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		model.addAttribute("pageBar", pageBar);
		
		List<Member> memberList = employeeService.getMemberList(rowBounds);
		model.addAttribute("memberList", memberList);
		
		//업데이트할 때 select 박스에 넣을 부서값, 직급값
		Map<String, String> param2 = new HashMap<>();
		
		param2.put("searchCode", "COM001");
		List<Map<String, String>> departmentList = commonCodeService.selectCmmnCodeList(param2);	
		model.addAttribute("departmentList", departmentList);

		param2.put("searchCode", "COM002");
		List<Map<String, String>> positionList = commonCodeService.selectCmmnCodeList(param2);
		
		model.addAttribute("positionList", positionList);
		model.addAttribute("emptyMsg", emptyMsg);
		
		return "manager/employee";
	}
	
	@ResponseBody
	@RequestMapping("/withdraw")
	public String withdrawEmployee(String email, @LoginUser Member member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("adminEmail", member.getEmail());
		param.put("email", email);
		
		int resultCnt = employeeService.withdrawEmployee(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String updateEmployee(Member member, @LoginUser Member loginMember) throws Exception {

		Map<String, Object> param = new HashMap<>();
		
		param.put("adminEmail", loginMember.getEmail());
		param.put("email", member.getEmail());
		param.put("name", member.getName());
		param.put("department", member.getDepartment());
		param.put("position", member.getPosition());
		param.put("esntlId", member.getEsntlId());
		
		int resultCnt = employeeService.updateEmployee(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
		
	}
}
