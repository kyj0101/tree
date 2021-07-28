
package com.vtex.tree.attendance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.attendance.mapper.AttendanceMapper;
import com.vtex.tree.attendance.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceMapper attendanceMapper;
}
