package com.vtex.tree.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.mapper.SecurityMapper;


@Service
public class SecurityDetails implements UserDetailsService{
	
	@Autowired
	private SecurityMapper securityMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = securityMapper.login(username);
		return member;
	}
	

}
