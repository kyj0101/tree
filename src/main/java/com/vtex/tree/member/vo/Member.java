package com.vtex.tree.member.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class Member implements Serializable, UserDetails{
	
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

	public Member() {
	}

	public Member(String email, String name, String phone, String birth, String password, String department, String position, String emillKey, String frstRegisterId, String lastUpdusrId, String roleCode, String zipCode, String address, String detailAddress) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
		this.password = password;
		this.department = department;
		this.position = position;
		this.emillKey = emillKey;
		this.frstRegisterId = frstRegisterId;
		this.lastUpdusrId = lastUpdusrId;
		this.roleCode = roleCode;
		this.zipCode = zipCode;
		this.address = address;
		this.detailAddress = detailAddress;
	}

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

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Member) {
			
			Member otherMember = (Member)obj;
			String otherEsntlId = otherMember.getEsntlId(); 
			
			if(otherEsntlId.equals(this.esntlId)) {
				return true;
			}
		}
		
		return false;
	}
}
