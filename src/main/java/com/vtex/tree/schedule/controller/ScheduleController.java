package com.vtex.tree.schedule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.category.board.vo.CategoryBoardVO;
import com.vtex.tree.category.chat.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.schedule.service.ScheduleService;
import com.vtex.tree.schedule.vo.ScheduleVO; 

@RequestMapping("/schedule")
@Controller
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private CategoryBoardService categoryBoardService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private ProjectService projectService;
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	@RequestMapping("/view")
	public String calendarView(@RequestParam String projectId, 
								Model model,
								HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();

		//현재 카테고리 
		Map<String, Object> categoryMap = categoryBoardService.getCategory(1);
		categoryMap.put("categoryName", "일정");
		model.addAttribute("categoryMap", categoryMap);

		List<ProjectVO> projectList = projectService.getMembersProject(member.getEsntlId());
		model.addAttribute("projectList", projectList);
	
		for(ProjectVO project : projectList) {

			param.put("projectId", project.getProjectId());
			param.put("esntlId", member.getEsntlId());
			
			List<CategoryBoardVO> categoryBoardList = projectService.getProjectBoardList(param);
			project.setCategoryBoardList(categoryBoardList);

			List<ChatRoomVO> chatRoomList = projectService.getProjectChatRoomList(param);
			project.setChatRoomList(chatRoomList);
		}	
		
		boolean isIn = attendanceService.isIn(member.getEmail()) > 0;
		boolean isOut = attendanceService.isOut(member.getEmail()) > 0;
		
		model.addAttribute("isIn", isIn);
		model.addAttribute("isOut", isOut);
	
		model.addAttribute("emptyMsg", emptyMsg);
		model.addAttribute("projectId", projectId);
		
		return "schedule/schedule";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public String insertSchedule(ScheduleVO schedule, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
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
