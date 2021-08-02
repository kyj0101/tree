package com.vtex.tree.member.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vtex.tree.member.service.MemberService;
import com.vtex.tree.member.vo.MemberVO;
import static com.vtex.tree.common.util.EncryptedPassword.*;
@RequestMapping("/member")
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/mypage/update/view")
	public String myPageUpdateView(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		String birth = member.getBirth();
		
		if(!birth.contains("-")) {

			String year = birth.substring(0,4) + "-";
			String month = birth.substring(4,6) + "-";
			String day = birth.substring(6,8);
			
			member.setBirth(year + month + day);
		}

		List<String> departmentList = memberService.getDepartmentList();
		List<String> positionList = memberService.getPositionList();
		
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
	
	@RequestMapping("/mypage/update/password")
	public String myPageUpdatePassword(String password, 
										String newPassword, 
										HttpServletRequest request, 
										RedirectAttributes redirectAttribute) {
		try {
			HttpSession session = request.getSession();
			MemberVO member = (MemberVO)session.getAttribute("loginMember");
			
			if(!member.getPassword().equals(getEncryptedPassword(password))) {
				
				redirectAttribute.addFlashAttribute("msg", "비밀번호가 일치하지 않습니다.");
				return "redirect:/member/mypage/update/password/view";
			}
			
			Map<String, String> param = new HashMap<>();
			String encryptedPassword = getEncryptedPassword(newPassword);
			
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
}
