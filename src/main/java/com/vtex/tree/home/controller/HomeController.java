package com.vtex.tree.home.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vtex.tree.common.util.TemporailyPassword;
import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.service.MemberService;
import com.vtex.tree.member.vo.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final HomeService homeService;
	private final CommonCodeService commonCodeService;
	private final MemberService memberService;

	private final JavaMailSenderImpl mailSender;

	private final PasswordEncoder passwordEncoder;
	
	@Value("${ip}")
	String ip;
	
	@Value("${server.port}")
	String port;
	
	/**
	 * 홈페이지
	 * 
	 * @return
	 */
	@PreAuthorize("permitAll()")
	@RequestMapping("/")
	public String home() {
		return "/home/home";
	}

	/**
	 * 로그인 페이지
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "home/login";
	}

	/**
	 * 회원가입 페이지
	 * 
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("permitAll()")
	@RequestMapping("/signup")
	public String signUp(Model model) throws Exception {

		// 부서 목록
		Map<String, String> param = new HashMap<>();
		param.put("searchCode", "COM001");

		List<Map<String, String>> departmentList = commonCodeService.selectCmmnCodeList(param);

		model.addAttribute("departmentList", departmentList);

		// 직급 목록
		param.put("searchCode", "COM002");

		List<Map<String, String>> positionList = commonCodeService.selectCmmnCodeList(param);

		model.addAttribute("positionList", positionList);
		
		//이메일 목록
		param.put("searchCode", "EMAIL");

		List<Map<String, String>> emailList = commonCodeService.selectCmmnCodeList(param);

		model.addAttribute("emailList", emailList);

		return "home/signup";
	}

	/**
	 * 이메일 중복검사
	 * 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/email/duplication/check")
	public String emailDuplicationCheck(String email) {
		System.out.println(email);
		boolean isDuplicate = homeService.emailDuplicationCheck(email);

		return isDuplicate ? "true" : "false";
	}

	/**
	 * 이메일 인증 페이지
	 * 
	 * @param model
	 * @param key   이메일 인증키
	 * @param email 이메일 주소
	 * @return
	 */
	@PreAuthorize("permitAll()")
	@RequestMapping("/email/verify")
	public String emailVerify(Model model, String key, String email) {

		model.addAttribute("key", key);
		model.addAttribute("email", email);

		return "home/emailVerify";
	}

	/**
	 * 회원 가입 처리
	 * 
	 * @param member
	 * @param domain
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/signupAction")
	public String signUp(MemberVO member, String domain) throws MessagingException, UnsupportedEncodingException {

		String password = passwordEncoder.encode(member.getPassword());
		String key = UUID.randomUUID().toString();

		member.setBirth(member.getBirth().replaceAll("-", ""));
		member.setPassword(password);
		member.setEmillKey(key);
		member.setEmail(member.getEmail() + domain);
		member.setFrstRegisterId(member.getEmail());
		member.setLastUpdusrId(member.getEmail());

		String url = "https://" + ip + ":" +  port + "/email/verify" + "?key=" + key + "&email=" + member.getEmail();

		try {
			homeService.insertMember(member);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom("Tree", "Tree");
			helper.setTo(member.getEmail());
			helper.setSubject("Tree 이메일 인증입니다.");
			helper.setText("<p>안녕하세요.</p>" + "<p>다음 링크를 통해 이메일 인증을 완료하세요.</p>" + "<p><a href='" + url
					+ "'>이메일 인증</a></p>" + "<p>감사합니다.</p>" + "<p>- Tree -</p>", true);

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
	 * 
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

			if (result > 0) {
				redirectAttribute.addFlashAttribute("msg", "이메일 인증이 완료되었습니다.");
				return "redirect:/login";
			}

			redirectAttribute.addFlashAttribute("msg", "이메일 인증을 실패했습니다.");
			return "redirect:/";

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	@RequestMapping("/find/password/view")
	public String findPasswordView() {
		return "home/findPassword";
	}
	
	@ResponseBody
	@RequestMapping("/find/password")
	public String findPassword(String email) {
		
		String tempoPwd =  TemporailyPassword.getTemporailyPassword(8);
		Map<String, String> param = new HashMap<>();
		
		param.put("password", passwordEncoder.encode(tempoPwd));
		param.put("email", email);
		
		memberService.updatePassword(param);
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		try {
			helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("Tree", "Tree");
			helper.setTo(email);
			helper.setSubject("Tree 임시 비밀번호 입니다.");
			helper.setText(("<p>임시 비밀번호: " + tempoPwd + "</p><p>감사합니다.</p>" + "<p>- Tree -</p>"), true);
			
			mailSender.send(message);
			
			return "ok";
		
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return "fail";
		}

	}

}
