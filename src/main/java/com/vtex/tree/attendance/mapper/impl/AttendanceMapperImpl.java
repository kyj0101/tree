package com.vtex.tree.attendance.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.attendance.mapper.AttendanceMapper;

@Repository
public class AttendanceMapperImpl implements AttendanceMapper {

	@Autowired
	private SqlSession session;
}
