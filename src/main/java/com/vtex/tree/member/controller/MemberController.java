package com.vtex.tree.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.service.MemberService;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.annotation.LoginUser;
@RequestMapping("/member")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final CommonCodeService commonCodeService;
	private final HomeService homeService;
	private final PasswordEncoder passwordEncoder;
	

	@RequestMapping("/mypage/update/view")
	public String myPageUpdateView(Model model, @LoginUser Member member) throws Exception {
		
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
								String zipCode,
								String address,
								String detailAddress,
								HttpServletRequest request,
								Authentication oldAuthentication) {
		

		try {
			Map<String, Object> param = new HashMap<>();
			
			param.put("email", email);
			param.put("name", name);
			param.put("phone", phone);
			param.put("birth", birth.replaceAll("-", ""));
			param.put("department", department);
			param.put("position", position);
			param.put("zipCode", zipCode);
			param.put("address", address);
			param.put("detailAddress", detailAddress);
			
			memberService.updateMember(param);
			
			HttpSession session = request.getSession();
			Member member = homeService.selectOneMember(email);
			
			session.setAttribute("loginMember", member);
	
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(member, oldAuthentication.getCredentials(), oldAuthentication.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return "redirect:/member/mypage/update/view";
	}
	

	
	@RequestMapping("/mypage/withdraw/view")
	public String myPageWithdrawView(Model model) throws Exception {
		
		Map<String, String> param = new HashMap<>();
		param.put("searchCode", "RN000");
		
		List<Map<String, String>> reasonList = commonCodeService.selectCmmnCodeList(param);
		
		model.addAttribute("reasonList", reasonList);
		return "mypage/withdraw"; 
	}
	

	
	@ResponseBody
	@RequestMapping("/mypage/withdraw")
	public String myPageWithdraw(String password, 
									String reasonCode, 
									HttpServletRequest request,
									@LoginUser Member member) throws Exception {

		HttpSession session = request.getSession();
	
		if(passwordEncoder.matches(password, member.getPassword())) {
			Map<String, Object> param = new HashMap<>();
			
			param.put("reasonCode", reasonCode);
			param.put("email", member.getEmail());
			param.put("password", passwordEncoder.encode(password));
			
			memberService.withdraw(param);
			
			if(session != null) {
				session.invalidate();			
			}
			
			return "ok";	
		}
		
		return "fail";
	}
	

}
