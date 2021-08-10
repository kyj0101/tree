package com.vtex.tree.employee.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.employee.mapper.EmployeeMapper;
import com.vtex.tree.employee.service.EmployeeService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public List<MemberVO> getMemberList(RowBounds rowBounds) throws Exception {
		return employeeMapper.getMemberList(rowBounds);
	}

	@Override
	public int getMemberListCnt() throws Exception {
		return employeeMapper.getMemberListCnt();
	}

	@Override
	public MemberVO getMemberDetail(String email) throws Exception {
		return employeeMapper.getMemberDetail(email);
	}
}
