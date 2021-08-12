package com.vtex.tree.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.member.service.MemberService;
import com.vtex.tree.member.vo.MemberVO;
@RequestMapping("/member")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@RequestMapping("/mypage/update/view")
	public String myPageUpdateView(Model model, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		String birth = member.getBirth();
		
		if(!birth.contains("-")) {

			String year = birth.substring(0,4) + "-";
			String month = birth.substring(4,6) + "-";
			String day = birth.substring(6,8);
			
			member.setBirth(year + month + day);
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("searchCode", "COM001");
		List<Map<String, String>> departmentList = commonCodeService.selectCmmnCodeList(param);
		

		param.put("searchCode", "COM002");
		List<Map<String, String>> positionList = commonCodeService.selectCmmnCodeList(param);
		
		model.addAttribute("member", member);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("positionList", positionList);

		return "mypage/update";
	}
		
	@RequestMapping("/mypage/update/update")
	public String myPageUpdate(String email,
								String name,
								String phone,
								String birth,
								String department,
								String position,
								HttpServletRequest request) {
		

		try {
			Map<String, Object> param = new HashMap<>();
			
			param.put("email", email);
			param.put("name", name);
			param.put("phone", phone);
			param.put("birth", birth.replaceAll("-", ""));
			param.put("department", department);
			param.put("position", position);
			
			memberService.updateMember(param);
			
			HttpSession session = request.getSession();
			MemberVO member = (MemberVO)session.getAttribute("loginMember");
			
			member.setName(name);
			member.setPhone(phone);
			member.setBirth(birth);
			member.setDepartment(department);
			member.setPosition(position);
			
			session.setAttribute("loginMember", member);
		
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return "redirect:/member/mypage/update/view";
	}
	
	@RequestMapping("/mypage/update/password/view")
	public String myPageUpdatePasswordView() {
		return "mypage/updatePassword"; 
	}
	
	@RequestMapping("/mypage/withdraw/view")
	public String myPageWithdrawView(Model model) throws Exception {
		
		Map<String, String> param = new HashMap<>();
		param.put("searchCode", "RN000");
		
		List<Map<String, String>> reasonList = commonCodeService.selectCmmnCodeList(param);
		
		model.addAttribute("reasonList", reasonList);
		return "mypage/withdraw"; 
	}
	
	@RequestMapping("/mypage/update/password")
	public String myPageUpdatePassword(String password, 
										String newPassword, 
										HttpServletRequest request, 
										RedirectAttributes redirectAttribute) {
		try {
			HttpSession session = request.getSession();
			MemberVO member = (MemberVO)session.getAttribute("loginMember");

			Map<String, String> param = new HashMap<>();
			String encryptedPassword =  passwordEncoder.encode(newPassword);

			param.put("password", encryptedPassword);
			param.put("email", member.getEmail());
			
			memberService.updatePassword(param); 
			member.setPassword(encryptedPassword);
			session.setAttribute("loginMember", member);
			redirectAttribute.addFlashAttribute("msg", "비밀번호가 변경되었습니다.");
					
			return "redirect:/member/mypage/update/password/view";
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	@ResponseBody
	@RequestMapping("/mypage/withdraw")
	public String myPageWithdraw(String password, String reasonCode, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		
		if(passwordEncoder.matches(password, member.getPassword())) {
			Map<String, Object> param = new HashMap<>();
			
			param.put("reasonCode", reasonCode);
			param.put("email", member.getEmail());
			
			memberService.withdraw(param);
			
			if(session != null) {
				session.invalidate();			
			}
			
			return "ok";	
		}
		
		return "fail";
	}
}
