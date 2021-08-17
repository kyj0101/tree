package com.vtex.tree.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.vtex.tree.home.service.HomeService;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private HomeService homeService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("======================================================");
		System.out.println(authentication.getDetails());
		
		homeService.setLogout("");
	}

}
