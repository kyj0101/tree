package com.vtex.tree.home.controller;

import static com.vtex.tree.common.util.EncryptedPassword.getEncryptedPassword;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.vo.MemberVO;

@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	/**
	 * 홈페이지
	 * @return
	 */
	@RequestMapping("/")
	public String home() {
		List<String> memberList = homeService.getMemberList();
	
		return "/home/home";	
	}
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "home/login";
	}
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@RequestMapping("/signup")
	public String signUp() {
		return "home/signup";
	}
	
	/**
	 * 이메일 중복검사
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/email/duplication/check")
	public String emailDuplicationCheck(String email) {
		boolean isDuplicate = homeService.emailDuplicationCheck(email);
		
		return isDuplicate ? "true" : "false";
	}
	
	/**
	 * 이메일 인증 페이지
	 * @param model
	 * @param key 이메일 인증키
	 * @param email 이메일 주소
	 * @return
	 */
	@RequestMapping("/email/verify")
	public String emailVerify(Model model, String key, String email) {
		
		model.addAttribute("key", key);
		model.addAttribute("email", email);
		
		return "home/emailVerify";
	}
	
	/**
	 * 회원 가입 처리
	 * @param member
	 * @param domain
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/signup")
	public String signUp(MemberVO member, String domain) throws MessagingException, UnsupportedEncodingException {
		
		String password = getEncryptedPassword(member.getPassword());
		String key = UUID.randomUUID().toString();
		
		member.setBirth(member.getBirth().replaceAll("-", ""));
		member.setPassword(password);
		member.setEmillKey(key);
		member.setEmail(member.getEmail() + domain);
		member.setFrstRegisterId(member.getEmail());
		member.setLastUpdusrId(member.getEmail());

		String url = "http://localhost:9090/email/verify" + "?key=" + key + "&email=" + member.getEmail(); 

		try {
			homeService.insertMember(member);
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setFrom("Tree", "Tree");
			helper.setTo(member.getEmail());
			helper.setSubject("Tree 이메일 인증입니다.");
			helper.setText("<p>안녕하세요.</p>"
                         + "<p>다음 링크를 통해 이메일 인증을 완료하세요.</p>"
					     + "<p><a href='" + url + "'>이메일 인증</a></p>"
					     + "<p>감사합니다.</p>"
					     + "<p>- Tree -</p>", true);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			throw e;
			
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		
		return "redirect:/";
	}
	
	/**
	 * 이메일 인증 처리
	 * @param email
	 * @param key
	 * @param redirectAttribute
	 * @return
	 */
	@PostMapping("/email/verify")
	public String emailVerify(String email, String key, RedirectAttributes redirectAttribute) {
		Map<String, String> param = new HashMap<>();
		param.put("email", email);
		param.put("key", key);
		
		try {
			int result = homeService.updateEmailVerify(param);
			
			if(result > 0) {
				redirectAttribute.addFlashAttribute("msg", "이메일 인증이 완료되었습니다.");
				return "redirect:/login";			
			}
			
			redirectAttribute.addFlashAttribute("msg", "이메일 인증을 실패했습니다.");
			return "redirect:/";
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 로그인 처리
	 * @param email
	 * @param password
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login/check")
	public String loginCheck(String email, String password, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO member = homeService.selectOneMember(email);
		String inputPassword = getEncryptedPassword(password);
		
		if(member == null) {
			return "FAIL";
		}
		
		if(member.getEmailVerifyPnttm() == null) {
			return "EMAIL";
		}
		
		if(member.getPassword().equals(inputPassword)) {
			session.setAttribute("loginMember", member);
			return "OK";
		}
		
		return "FAIL";
	} 
	
	/**
	 * 로그아웃 처리
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();			
		}
		
		return "redirect:/";
	}
	
	
	
	
}
