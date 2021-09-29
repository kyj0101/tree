package com.vtex.tree.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.common.util.AttendanceUtil;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.schedule.service.ScheduleService;
import com.vtex.tree.schedule.vo.ScheduleVO;
import com.vtex.tree.security.annotation.LoginUser; 

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/schedule")
@Controller
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private CategoryService categoryBoardService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/view")
	public ModelAndView scheduleView(@RequestParam String projectId, 
								ModelAndView model,
								@LoginUser MemberVO member) throws Exception {
		//현재 프로젝트
		ProjectVO project = projectService.getProject(projectId);

		SimpleDateFormat projectFormat = new SimpleDateFormat("yyyy-MM-dd");

		project.setStartDate(AttendanceUtil.updateDayFormat(project.getStartDate(), projectFormat));
		project.setEndDate(AttendanceUtil.updateDayFormat(project.getEndDate(), projectFormat));
		
		model.addObject("project", project); 
		model.addObject("selectedProjectId", projectId);
		model.addObject("category", 1);
		model.addObject("esntlId", member.getEsntlId());
		model.addObject("email", member.getEmail());
		model.setViewName("schedule/schedule");
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public String insertSchedule(ScheduleVO schedule, @LoginUser MemberVO member) throws Exception {
		
		String loginEsntlId = member.getEsntlId();
		
		schedule.setFrstRegisterId(loginEsntlId);
		schedule.setLastUpdusrId(loginEsntlId);
		
		int resultCnt = scheduleService.insertSchedule(schedule);
		
		return resultCnt > 0 ? "ok":"fail"; 
	}
	
	@ResponseBody
	@RequestMapping("/get/schedulelist")
	public List<ScheduleVO> getScheduleList(String projectId) throws Exception {
		
		List<ScheduleVO> scheduleList = scheduleService.getScheduleList(projectId);
		
		return scheduleList;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String updateSchedule(ScheduleVO schedule) throws Exception {
		
		int resultCnt = scheduleService.updateSchedule(schedule);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String deleteSchedule(String id) throws Exception {
		
		int resultCnt = scheduleService.deleteSchedule(id);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
}
