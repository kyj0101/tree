package com.vtex.tree.security.service;

import java.lang.reflect.Member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.security.mapper.SecurityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;



@Service
public class SecurityDetails implements UserDetailsService{
	
	@Autowired
	private SecurityMapper securityMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO member = securityMapper.login(username);		
		return member;
	}
	

}
