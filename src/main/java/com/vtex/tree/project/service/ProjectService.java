package com.vtex.tree.project.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.vo.ProjectVO;

public interface ProjectService {

	int insertProject(ProjectVO project) throws Exception;

	List<ProjectVO> getProjectList(RowBounds rowBounds) throws Exception;

	int getTotalProject() throws Exception;

	ProjectVO selectOneProject(Map<String, Object> param) throws Exception;

	List<MemberVO> getMemberList(Map<String, Object> param) throws Exception;

	int insertProjectMember(Map<String, Object> param) throws Exception;

	List<MemberVO> getProjectMemberList(Map<String, Object> param, RowBounds rowBounds) throws Exception;

	int getTotalProjectMember(String projectId) throws Exception;

	int updateProjectRole(Map<String, Object> param) throws Exception;

	int updateProjectManager(Map<String, Object> param) throws Exception;

	int updateProjectManangerToUser(Map<String, Object> param) throws Exception;

	int deleteProjectMember(Map<String, Object> param) throws Exception;

	int insertProjectNote(Map<String, Object> param) throws Exception;

	int deleteProject(String projectId) throws Exception;

	List<ProjectVO> getMembersProject(String esntlId) throws Exception;

	List<CategoryVO> getProjectBoardList(Map<String, Object> param) throws Exception;

	List<ChatRoomVO> getProjectChatRoomList(Map<String, Object> param) throws Exception;

	int updateProject(Map<String, Object> param) throws Exception;

	ProjectVO getProject(String projectId); 

}
