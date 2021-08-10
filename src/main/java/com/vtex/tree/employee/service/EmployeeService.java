package com.vtex.tree.employee.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.member.vo.MemberVO;

public interface EmployeeService {

	List<MemberVO> getMemberList(RowBounds rowBounds) throws Exception;
	

	int getMemberListCnt() throws Exception;


	MemberVO getMemberDetail(String email) throws Exception;

}
