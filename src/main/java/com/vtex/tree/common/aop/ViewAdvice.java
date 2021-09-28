package com.vtex.tree.common.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.service.ChatRoomService;
import com.vtex.tree.chat.room.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;
import com.vtex.tree.security.annotation.LoginUser;

@Service
@Aspect
public class ViewAdvice {
	
	@Autowired
	private ProjectService projectService;
		
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ChatRoomService chatRoomSevice;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Pointcut("execution(* com.vtex.tree..*Controller.*View(..))")
	public void defaultPointcut() {};
	
	@Pointcut("execution(* com.vtex.tree..*Controller.getChatRoom(..))")
	public void chatRoomPointcut() {};
	
	@Around("defaultPointcut()")
	public Object setDefaultView(ProceedingJoinPoint pjp) throws Throwable {

		Object obj = pjp.proceed();
		
		if(obj instanceof ModelAndView) {
			
			ModelAndView model = (ModelAndView)obj;
			Map<String, Object> modelMap = model.getModelMap();
			
			setModelAndViewProjectList(model, modelMap);
			setModelAndViewCategoryMap(model, modelMap);
			setModelAndViewIsInOrIsOut(model, modelMap);			
		}
		
		return obj;
	}
	
	@Around("chatRoomPointcut()")
	public Object setChatRoomView(ProceedingJoinPoint pjp) throws Throwable {

		Object obj = pjp.proceed();
		
		if(obj instanceof ModelAndView) {
			
			ModelAndView model = (ModelAndView)obj;
			Map<String, Object> modelMap = model.getModelMap();
			
			setModelAndViewProjectList(model, modelMap);
			setModelAndViewChatRoom(model, modelMap);
			setModelAndViewIsInOrIsOut(model, modelMap);			
		}
		
		return obj;
	}
	
	//프로젝트 목록
	private ModelAndView setModelAndViewProjectList(ModelAndView model, Map<String, Object> modelMap) throws Exception {
		
		List<ProjectVO> projectList = projectService.getMembersProject((String)modelMap.get("esntlId"));
		model.addObject("projectList", projectList);
		
		//프로젝트의 게시판,채팅방 목록
		for(ProjectVO project : projectList) {

			modelMap.put("projectId", project.getProjectId());
			
			List<CategoryVO> categoryBoardList = projectService.getProjectBoardList(modelMap);
			project.setCategoryBoardList(categoryBoardList);
			
			List<ChatRoomVO> chatRoomList = projectService.getProjectChatRoomList(modelMap);
			project.setChatRoomList(chatRoomList);
		
		}
		
		return model;
	}
	
	//현재 선택된 게시판 카테고리
	private ModelAndView setModelAndViewCategoryMap(ModelAndView model, Map<String, Object> modelMap) throws Exception {
		
		Map<String, Object> categoryMap = categoryService.getCategory(Integer.parseInt(modelMap.get("category") + ""));
		model.addObject("categoryMap", categoryMap);
		
		if(categoryMap.get("projectId") == null) {
			
			categoryMap.put("projectId", "notice");		
			categoryMap.put("projectNm", "");		
		}
		
		return model;
	}
	
	//현재 선택된 채팅방
	private ModelAndView setModelAndViewChatRoom(ModelAndView model, Map<String, Object> modelMap) throws Exception {
		
		Map<String, Object> categoryMap = chatRoomSevice.getChatRoom(Integer.parseInt(modelMap.get("category") + ""));
		model.addObject("categoryMap", categoryMap);
		
		if(categoryMap.get("projectId") == null) {
			
			categoryMap.put("projectId", "notice");		
			categoryMap.put("projectNm", "");		
		}
		
		return model;
	}
	
	//출퇴근 여부
	private ModelAndView setModelAndViewIsInOrIsOut(ModelAndView model, Map<String, Object> modelMap) throws Exception {
		
		String email = modelMap.get("email") + "";
		
		boolean isIn = attendanceService.isIn(email) > 0;
		boolean isOut = attendanceService.isOut(email) > 0;
		
		model.addObject("isIn", isIn);
		model.addObject("isOut", isOut);
		
		return model;
	}
}
