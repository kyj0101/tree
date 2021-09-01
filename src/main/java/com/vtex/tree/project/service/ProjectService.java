package com.vtex.tree.project.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.category.board.vo.CategoryBoardVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.vo.ProjectVO;

public interface ProjectService {

	int insertProject(ProjectVO project) throws Exception;

	List<ProjectVO> getProjectList(RowBounds rowBounds) throws Exception;

	int getTotalProject() throws Exception;

	ProjectVO selectOneProject(String projectId) throws Exception;

	List<MemberVO> getMemberList(Map<String, Object> param) throws Exception;

	int insertProjectMember(Map<String, Object> param) throws Exception;

	List<MemberVO> getProjectMemberList(String projectId, RowBounds rowBounds) throws Exception;

	int getTotalProjectMember(String projectId) throws Exception;

	int updateProjectRole(Map<String, Object> param) throws Exception;

	int updateProjectManager(Map<String, Object> param) throws Exception;

	int updateProjectManangerToUser(Map<String, Object> param) throws Exception;

	int deleteProjectMember(Map<String, Object> param) throws Exception;

	int insertProjectNote(Map<String, Object> param) throws Exception;

	int deleteProject(String projectId) throws Exception;

	List<ProjectVO> getMembersProject(String esntlId) throws Exception;

	List<CategoryBoardVO> getProjectBoardList(Map<String, Object> param) throws Exception; 

}
