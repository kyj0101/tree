package com.vtex.tree.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailHandler implements AuthenticationFailureHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			 AuthenticationException exception) throws IOException, ServletException {
		
		String msg =  "로그인을 실패했습니다.";
		
		if(exception instanceof InternalAuthenticationServiceException) {
			msg =  "존재하지 않는 회원입니다.";
		
		}else if (exception instanceof BadCredentialsException) {
			msg =  "아이디 또는 비밀번호가 다릅니다.";
			
		}else if (exception instanceof OAuth2AuthenticationException) {
			msg = "해당 아이디로 연동된 계정을 찾을 수 없습니다.";
		}
		
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/login").forward(request, response);
	}	
	
}
	