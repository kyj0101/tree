package com.vtex.tree.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.employee.service.EmployeeService;

@RequestMapping("/employee")
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@RequestMapping("/list")
	public String getEmployeeList() {
		return "manager/employee";
	}
}
