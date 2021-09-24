package com.vtex.tree.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.vtex.tree.member.vo.MemberVO;


public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Object proincipalObj = authentication.getPrincipal();
		
		if(proincipalObj instanceof MemberVO) {
			
			MemberVO member = (MemberVO)proincipalObj;
			
			request.getSession().setAttribute("loginMember", member);
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
