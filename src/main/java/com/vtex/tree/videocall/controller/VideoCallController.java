package com.vtex.tree.videocall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.security.annotation.LoginUser;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/videocall")
@Controller
@RequiredArgsConstructor
public class VideoCallController {
	

	private final AttendanceService attendanceService;
	private final ProjectService projectService;
	private final CategoryService categoryBoardService;
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	@RequestMapping("/view")
	public String videoCallView(@RequestParam(required = true) String videoCallId, 
								@RequestParam(required = true) String type,
								@LoginUser MemberVO member, 
								Model model) throws Exception {

		//카테고리 리스트 
		List<ProjectVO> projectList = projectService.getMembersProject(member.getEsntlId());
		model.addAttribute("projectList", projectList);
		
		Map<String, Object> param = new HashMap<>();
		
		for(ProjectVO project : projectList) {

			param.put("projectId", project.getProjectId());
			param.put("esntlId", member.getEsntlId());
			
			List<CategoryVO> categoryBoardList = projectService.getProjectBoardList(param);
			project.setCategoryBoardList(categoryBoardList);

			List<ChatRoomVO> chatRoomList = projectService.getProjectChatRoomList(param);
			project.setChatRoomList(chatRoomList);
			
		}

		//현재 카테고리 
		Map<String, Object> categoryMap = categoryBoardService.getCategory(1);
		categoryMap.put("categoryName", "화상통화");
		model.addAttribute("categoryMap", categoryMap);
		
		//출퇴근 여부
		boolean isIn = attendanceService.isIn(member.getEmail()) > 0;
		boolean isOut = attendanceService.isOut(member.getEmail()) > 0;
		
		model.addAttribute("isIn", isIn);
		model.addAttribute("isOut", isOut);
		model.addAttribute("emptyMsg", emptyMsg);
		model.addAttribute("videoCallId", videoCallId);
		model.addAttribute("type", type);
		
		return "videocall/videoCall";
	}
	
}
