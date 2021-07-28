package com.vtex.tree.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.attendance.service.AttendanceService;

@RequestMapping("/attendance")
@Controller
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	
	@RequestMapping("/list")
	public String getAttendanceList() {
		return "manager/attendance";
	}

}
