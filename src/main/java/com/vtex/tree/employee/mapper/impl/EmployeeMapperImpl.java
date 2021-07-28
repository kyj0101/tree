package com.vtex.tree.employee.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.employee.mapper.EmployeeMapper;

@Repository
public class EmployeeMapperImpl implements EmployeeMapper{
	
	@Autowired
	private SqlSession session;
}
