package com.vtex.tree.home.controller;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;

import com.vtex.tree.emailverify.sevice.EmailVerifyService;
import com.vtex.tree.emailverify.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.service.MemberService;
import com.vtex.tree.member.vo.Member;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final HomeService homeService;
	private final CommonCodeService commonCodeService;
	private final MemberService memberService;
	private final EmailVerifyService emailVerifyService;

	private final PasswordEncoder passwordEncoder;

	@Value("${ip}")
	private String ip;

	@Value("${server.port}")
	private String port;

	private final String EMAIL_VERIFY_SUBJECT = "Tree 이메일 인증입니다.";
	private final String EMAIL_VERIFY_TEXT = "<p>안녕하세요.</p>" + "<p>다음 링크를 통해 이메일 인증을 완료하세요.</p>" + "<p><a href=' url '>이메일 인증</a></p>" + "<p>감사합니다.</p>" + "<p>- Tree -</p>";


	@GetMapping("/")
	public String home() {
		return "home/home";
	}

	@GetMapping("/login")
	public String login() {
		return "home/login";
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/member")
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

	@PostMapping("/member")
	public String signUp(Member member, String domain) throws MessagingException, UnsupportedEncodingException {

		String password = passwordEncoder.encode(member.getPassword());
		String key = UUID.randomUUID().toString();

		member.setBirth(member.getBirth().replaceAll("-", ""));
		member.setPassword(password);
		member.setEmillKey(key);
		member.setEmail(member.getEmail() + domain);
		member.setFrstRegisterId(member.getEmail());
		member.setLastUpdusrId(member.getEmail());

		homeService.insertMember(member);
		emailVerifyService.emailSend(makeSignUpEmail(member));

		return "redirect:/";
	}

	private Email makeSignUpEmail(Member member) {

		String param = "?key=" + member.getEmillKey() + "&email=" + member.getEmail();
		String url = emailVerifyService.makeEmailUrl(ip, port, "/email/verify", param);

		return new Email(member.getEmail(), EMAIL_VERIFY_SUBJECT, EMAIL_VERIFY_TEXT.replace("url", url));
	}
}
