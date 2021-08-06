package com.vtex.tree.category.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface CategoryBoardMapper {
	
	List<MemberVO> getMemberList() throws Exception;
	
	int insertCategoryBoard(Map<String, Object> param);
	
	int insertCategoryBoardUser(Map<String, Object> param);
	
	int deleteCategoryBoard(String categoryNo) throws Exception;
}
