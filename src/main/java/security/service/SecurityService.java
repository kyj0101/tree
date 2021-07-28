package security.service;

import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vtex.tree.common.exception.MemberNotFountException;
import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.member.vo.MemberVO;

import security.mapper.SecurityMapper;


@Service
public class SecurityService implements UserDetailsService{

	@Autowired
	HomeMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		MemberVO member = mapper.selectOneMember(userName);
		System.out.println("??");
		System.out.println(member.toString());

		if (member == null) {
			throw new MemberNotFountException("MemberNotFoundException");
		}
		return member;

	}

}
