package com.vtex.tree.category.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.Member;

@Mapper
public interface CategoryMapper {
	
	List<Member> getMemberList(String email) throws Exception;
	
	int insertCategoryBoard(Map<String, Object> param);
	
	int insertCategoryBoardMember(Map<String, Object> param);
	
	List<Map<String, Object>> getCategoryList(String email);
	
	Map<String, Object> getCategory(int category);
	
	int deleteCategoryBoard(Map<String, Object> param) throws Exception;

	int outBoard(Map<String, Object> param) throws Exception;

	List<Member> getMemberListToInvite(String categoryNo) throws Exception;
}
