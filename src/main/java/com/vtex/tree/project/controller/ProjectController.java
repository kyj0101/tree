package com.vtex.tree.project.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.common.util.AttendanceUtil;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;

@RequestMapping("/project")
@Controller
public class ProjectController {
	
	private final int PROJECT_NUMPERPAGE = 3;
	
	private final int MEMBER_NUMPERPAGE = 10;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/list")
	public String projectList(@RequestParam(defaultValue = "1") int cPage, HttpServletRequest request, Model model) throws Exception {
		
		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", PROJECT_NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * PROJECT_NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, PROJECT_NUMPERPAGE);
		
		int totalContents = projectService.getTotalProject();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, PROJECT_NUMPERPAGE, url);
		
		List<ProjectVO> projectList = projectService.getProjectList(rowBounds);
		
		SimpleDateFormat dayForamt = new SimpleDateFormat("yyyy-MM-dd");
		
		for(ProjectVO project : projectList) {
			
			project.setStartDate(AttendanceUtil.updateDayFormat(project.getStartDate(), dayForamt));
			project.setEndDate(AttendanceUtil.updateDayFormat(project.getEndDate(), dayForamt));
		}
		
		model.addAttribute("pageBar", pageBar);
		model.addAttribute("projectList", projectList);
		
		return "manager/projectList";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public String insertProject(ProjectVO project, @AuthenticationPrincipal MemberVO member) throws Exception {
		
		String loginEsntlId = member.getEsntlId();
		
		project.setFrstRegisterId(loginEsntlId);
		project.setLastUpdusrId(loginEsntlId);
		project.setStartDate(project.getStartDate().replaceAll("-", ""));
		project.setEndDate(project.getEndDate().replaceAll("-", ""));
		
		int resultCnt = projectService.insertProject(project);
		
		Map<String, Object> param = new HashMap<>();
		param.put("esntlId", project.getProjectManager());
		param.put("projectId", project.getProjectId());
		param.put("loginEsntlId", loginEsntlId);
		param.put("projectRole", 'M');
		
		resultCnt += projectService.insertProjectMember(param);
		
		if(resultCnt > 1) {
			return "ok";
		}
		
		return "fail";
	}
	
	@RequestMapping("/setting/view")
	public String settingView(String projectId, 
								Model model, 
								@RequestParam(defaultValue = "1") int cPage, 
								HttpServletRequest request) throws Exception {
		
		ProjectVO project = projectService.selectOneProject(projectId);
		model.addAttribute("project", project);
		
		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", MEMBER_NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * MEMBER_NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, MEMBER_NUMPERPAGE);
		
		int totalContents = projectService.getTotalProjectMember(projectId);
		String url = request.getRequestURI() + "?projectId=" + projectId;
		String pageBar = getPageBar(totalContents, cPage, MEMBER_NUMPERPAGE, url);
		
		model.addAttribute("pageBar", pageBar);
		
		List<MemberVO> memberList = projectService.getProjectMemberList(projectId, rowBounds);
		model.addAttribute("memberList", memberList);
	
		return "manager/projectSetting";
	}
	
	@ResponseBody
	@RequestMapping("/memberList")
	public List<MemberVO> getMemberList(String projectId) throws Exception{
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		
		List<MemberVO> memberList = projectService.getMemberList(param); 
		
		return memberList;
	} 
	
	@ResponseBody
	@RequestMapping("/insert/member")
	public String insertProjectMember(@RequestParam(value="esntlIdList[]") List<String> esntlIdList, 
										@AuthenticationPrincipal MemberVO member,
										String projectId) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginEsntlId", member.getEsntlId());
		param.put("projectId", projectId);
		param.put("projectRole", 'U');
		
		int resultCnt = 0;
		
		for(String esntlId : esntlIdList) {
			
			param.put("esntlId", esntlId);
			resultCnt += projectService.insertProjectMember(param);
		}

		return resultCnt == esntlIdList.size() ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/update/role")
	public String updateProjectRole(String projectId, 
									String esntlId, 
									String role, 
									@AuthenticationPrincipal MemberVO member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("esntlId", esntlId);
		param.put("role", role);
		param.put("loginEsntlId", member.getEsntlId());
		
		int resultCnt = projectService.updateProjectRole(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/update/role/manager")
	public String updateProjectRoleManager(String projectId, 
											String esntlId, 
											String role, 
											@AuthenticationPrincipal MemberVO member) throws Exception{
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("esntlId", esntlId);
		param.put("role", role);
		param.put("loginEsntlId", member.getEsntlId());
		
		int resultCnt = projectService.updateProjectManangerToUser(param);
		resultCnt = projectService.updateProjectManager(param);
		resultCnt += projectService.updateProjectRole(param);
		
		return resultCnt > 1 ? "ok" : "fail";
	}
}
