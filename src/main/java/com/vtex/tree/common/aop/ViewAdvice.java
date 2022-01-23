package com.vtex.tree.common.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.service.ChatRoomService;
import com.vtex.tree.chat.room.vo.ChatRoomVO;
import com.vtex.tree.common.enums.ViewName;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;

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
	
	@Value("${empty.msg}")
	private String emptyMsg;
	
	@Pointcut("execution(* com.vtex.tree..*Controller.*View(..))")
	public void defaultPointcut() {};
	
	@Around("defaultPointcut()")
	public Object setDefaultView(ProceedingJoinPoint pjp) throws Throwable {

		Object obj = pjp.proceed();
		
		if(obj instanceof ModelAndView) {
			
			ModelAndView model = (ModelAndView)obj;
			Map<String, Object> modelMap = model.getModelMap();
			String view = model.getViewName();
			
			setModelAndViewProjectList(model, modelMap);
			
			if(ViewName.BOARD.getViewName().equals(view)) {
				setModelAndViewCategoryMap(model, modelMap);				
			
			}else if(ViewName.CHAT.getViewName().equals(view)){
				setModelAndViewChatRoom(model, modelMap);		
				
			}else if(ViewName.SCHEDULE.getViewName().equals(view)) {
				setModelAndViewSchedule(model, modelMap);
			}
			
			setModelAndViewIsInOrIsOut(model, modelMap);
			
			model.addObject("emptyMsg", emptyMsg);
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
		
		//만약 선택된 게시판이 공지사항이라면
		if((Integer.parseInt(categoryMap.get("categoryNo") + "")) == 1) {
			categoryMap.put("projectId", "-1");
			categoryMap.put("projectNm", "");
		}
		
		model.addObject("categoryMap", categoryMap);

		return model;
	}
	
	//현재 선택된 채팅방
	private ModelAndView setModelAndViewChatRoom(ModelAndView model, Map<String, Object> modelMap) throws Exception {
		
		Map<String, Object> categoryMap = chatRoomSevice.getChatRoom(Integer.parseInt(modelMap.get("category") + ""));
		model.addObject("categoryMap", categoryMap);

		return model;
	}
	
	//캘린더
	private ModelAndView setModelAndViewSchedule(ModelAndView model, Map<String, Object> modelMap) {
		
		Map<String, Object> categoryMap = new HashMap<>();
		
		categoryMap.put("projectId", modelMap.get("selectedProjectId"));
		categoryMap.put("categoryNo", "schedule");
		
		model.addObject("categoryMap", categoryMap);
		
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
