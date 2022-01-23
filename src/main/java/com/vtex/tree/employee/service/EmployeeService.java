package com.vtex.tree.employee.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.member.vo.Member;

public interface EmployeeService {

	List<Member> getMemberList(RowBounds rowBounds) throws Exception;
	
	int getMemberListCnt() throws Exception;

	int withdrawEmployee(Map<String, Object> param) throws Exception;

	int updateEmployee(Map<String, Object> param) throws Exception;

}
