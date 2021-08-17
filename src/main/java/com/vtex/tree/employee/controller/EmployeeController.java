package com.vtex.tree.employee.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.employee.service.EmployeeService;
import com.vtex.tree.member.vo.MemberVO;

@RequestMapping("/employee")
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployeeController {
	
	private final int NUMPERPAGE = 5;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	/**
	 * 직원 목록 
	 * @param model
	 * @param cPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String getEmployeeList(Model model, 
									@RequestParam(defaultValue = "1")int cPage,
									HttpServletRequest request) throws Exception {
		
		//직원 목록
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
	public String withdrawEmployee(String email, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		
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
	public String updateEmployee(MemberVO member, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginMember = (MemberVO)session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("adminEmail", loginMember.getEmail());
		param.put("email", member.getEmail());
		param.put("name", member.getName());
		param.put("department", member.getDepartment());
		param.put("position", member.getPosition());
		
		int resultCnt = employeeService.updateEmployee(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
		
	}
}
