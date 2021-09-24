package com.vtex.tree.project.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.common.util.AttendanceUtil;
import com.vtex.tree.commoncode.service.CommonCodeService;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.schedule.service.ScheduleService;
import com.vtex.tree.schedule.vo.ScheduleVO;

@RequestMapping("/project")
@Controller
public class ProjectController {
	
	private final int PROJECT_NUMPERPAGE = 3;
	
	private final int MEMBER_NUMPERPAGE = 10;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@Autowired
	private ScheduleService scheduleService;
	
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
	public String insertProject(ProjectVO project, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		String startDate = project.getStartDate(); 
		String endDate = project.getEndDate();
		
		//프로젝트 insert
		String loginEsntlId = member.getEsntlId();
		
		project.setFrstRegisterId(loginEsntlId);
		project.setLastUpdusrId(loginEsntlId);
		project.setStartDate(startDate.replaceAll("-", ""));
		project.setEndDate(endDate.replaceAll("-", ""));
		
		int resultCnt = projectService.insertProject(project);
		
		Map<String, Object> param = new HashMap<>();
		param.put("esntlId", project.getProjectManager());
		param.put("projectId", project.getProjectId());
		param.put("loginEsntlId", loginEsntlId);
		param.put("projectRole", 'M');
		
		resultCnt += projectService.insertProjectMember(param);
		
		if(resultCnt > 0) {
			return "ok";
		}
		
		return "fail";
	}
	
	@RequestMapping("/setting/view")
	public String settingView(String projectId, 
								Model model, 
								@RequestParam(defaultValue = "1") int cPage,
								@RequestParam(required = false) String searchKeyword,
								HttpServletRequest request) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("searchKeyword", searchKeyword);
		
		ProjectVO project = projectService.selectOneProject(param);
		
		//프로젝트 날짜 포멧을 변경함 ex 00000000 -> 0000-00-00
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

		project.setStartDate(AttendanceUtil.updateDayFormat(project.getStartDate(), dayFormat));
		project.setEndDate(AttendanceUtil.updateDayFormat(project.getEndDate(), dayFormat));
		model.addAttribute("project", project);
		
		//페이징 처리
		param.put("numPerPage", MEMBER_NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * MEMBER_NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, MEMBER_NUMPERPAGE);
		
		int totalContents = projectService.getTotalProjectMember(projectId);
		String url = request.getRequestURI() + "?projectId=" + projectId;
		String pageBar = getPageBar(totalContents, cPage, MEMBER_NUMPERPAGE, url);
		model.addAttribute("pageBar", pageBar);
		
		//프로젝트에 소속된 멤버리스트 
		List<MemberVO> memberList = projectService.getProjectMemberList(param, rowBounds);
		model.addAttribute("memberList", memberList);
		
		//직급, 부서 리스트 
		Map<String, String> codeParam = new HashMap<>();
		codeParam.put("searchCode", "COM001");
		List<Map<String, String>> departmentList = commonCodeService.selectCmmnCodeList(codeParam);
		model.addAttribute("departmentList",departmentList);
		
		codeParam.put("searchCode", "COM002");
		List<Map<String, String>> positionList = commonCodeService.selectCmmnCodeList(codeParam);
		model.addAttribute("positionList", positionList);
		
		return "manager/projectSetting";
	}
	
	@ResponseBody
	@RequestMapping("/memberList")
	public List<MemberVO> getMemberList(String projectId, 
										@RequestParam(required = false) String department,
										@RequestParam(required = false) String position) throws Exception{
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("department", department);
		param.put("position", position);
		
		List<MemberVO> memberList = projectService.getMemberList(param); 
		
		return memberList;
	} 
	
	@ResponseBody
	@RequestMapping("/insert/member")
	public String insertProjectMember(@RequestParam(value="esntlIdList[]") List<String> esntlIdList, 
										HttpServletRequest request,
										String projectId) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
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
	@RequestMapping("/insert/note")
	public String insertProjectNote(String projectId, String note, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("note", note);
		param.put("loginEsntlId", member.getEsntlId());
		
		int resultCnt = projectService.insertProjectNote(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/update/role")
	public String updateProjectRole(String projectId, 
									String esntlId, 
									String role, 
									HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
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
											HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
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
	
	@ResponseBody
	@RequestMapping("/update/project")
	public String updateProject(String projectId, 
									String projectNm, 
									String startDate, 
									String endDate,
									HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectNm", projectNm);
		param.put("projectId", projectId);
		param.put("startDate", startDate.replaceAll("-", ""));
		param.put("endDate", endDate.replaceAll("-", ""));
		param.put("loginEsntlId", member.getEsntlId());
		
		int resultCnt = projectService.updateProject(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/delete/member")
	public String deleteProjectMember(String projectId, String esntlId, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("projectId", projectId);
		param.put("esntlId", esntlId);
		param.put("loginEsntlId", member.getEsntlId());
		
		int resultCnt = projectService.deleteProjectMember(param);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
	
	@ResponseBody
	@RequestMapping("/delete/project")
	public String deleteProject(String projectId) throws Exception {
		
		int resultCnt = projectService.deleteProject(projectId);
		
		return resultCnt > 0 ? "ok" : "fail";
	}
}
