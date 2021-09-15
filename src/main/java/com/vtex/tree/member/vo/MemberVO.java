package com.vtex.tree.member.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MemberVO implements Serializable, UserDetails{
	
	private static final long serialVersionUID = 1L;

	private String email;
	private String name;
	private String phone;
	private String birth;
	private String password;
	private String department;
	private String position;
	private String emillKey;
	private String emailVerifyPnttm;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String quitAt;
	private String roleCode;
	private String departmentName;
	private String positionName;
	private Set<SimpleGrantedAuthority> authorities;
	private String zipCode;
	private String address;
	private String detailAddress;
	private String loginAt;
	private String esntlId;
	private String projectRole;
	private String sessionId;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if("ADMIN".equals(roleCode)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
