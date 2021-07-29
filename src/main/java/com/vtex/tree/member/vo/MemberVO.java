package com.vtex.tree.member.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
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

}
