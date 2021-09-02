package com.vtex.tree.project.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.board.vo.CategoryBoardVO;
import com.vtex.tree.category.chat.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.mapper.ProjectMapper;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Override
	public int insertProject(ProjectVO project) throws Exception {
		return projectMapper.insertProeject(project);
	}
	
	@Override
	public List<ProjectVO> getProjectList(RowBounds rowBounds) throws Exception {
		return projectMapper.getProjectList(rowBounds);
	}
	
	@Override
	public int getTotalProject() throws Exception{
		return projectMapper.getTotalProject();
	}
	
	@Override
	public ProjectVO selectOneProject(Map<String, Object> param) throws Exception {
		return projectMapper.selectOneProject(param);
	}
	
	@Override
	public List<MemberVO> getMemberList(Map<String, Object> param) throws Exception {
		return projectMapper.getMemberList(param);
	}
	
	@Override
	public int insertProjectMember(Map<String, Object> param) throws Exception {
		return projectMapper.insertProjectMember(param);
	}
	
	@Override
	public int insertProjectNote(Map<String, Object> param) throws Exception {
		return projectMapper.insertProjectNote(param);
	}
	
	@Override
	public List<MemberVO> getProjectMemberList(Map<String, Object> param, RowBounds rowBounds) throws Exception {
		return projectMapper.getProjectMemberList(param, rowBounds);
	}
	
	@Override
	public int getTotalProjectMember(String projectId) throws Exception {
		return projectMapper.getTotalProjectMember(projectId);
	}
	
	@Override
	public int updateProjectRole(Map<String, Object> param) throws Exception {
		return projectMapper.updateProjectRole(param);
	}
	
	@Override
	public int updateProjectManager(Map<String, Object> param) throws Exception {
		return projectMapper.updateProjectManager(param);
	}
	
	@Override
	public int updateProjectManangerToUser(Map<String, Object> param) throws Exception {
		return projectMapper.updateProjectManagerToUser(param);
	}
	
	@Override
	public int deleteProjectMember(Map<String, Object> param) throws Exception {
		return projectMapper.deleteProjectMember(param);
	}
	
	@Override
	public int deleteProject(String projectId) throws Exception {
		return projectMapper.deleteProject(projectId);
	}
	
	@Override
	public List<ProjectVO> getMembersProject(String esntlId) throws Exception {
		return projectMapper.getMembersProject(esntlId);
	}
	
	@Override
	public List<CategoryBoardVO> getProjectBoardList(Map<String, Object> param) throws Exception {
		return projectMapper.getProjectBoardList(param);
	}
	
	@Override
	public List<ChatRoomVO> getProjectChatRoomList(Map<String, Object> param) throws Exception {
		return projectMapper.getProjectChatRoomList(param);
	}
	
	@Override
	public int updateProject(Map<String, Object> param) throws Exception {
		return projectMapper.updateProject(param);
	}

}
