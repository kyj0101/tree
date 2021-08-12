package com.vtex.tree.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailHandler implements AuthenticationFailureHandler {
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			 AuthenticationException exception) throws IOException, ServletException {
		
		String msg =  "로그인을 실패했습니다.";

		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/login").forward(request, response);
		
	}	
	
}
	