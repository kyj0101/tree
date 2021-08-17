package com.vtex.tree.category.board.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface CategoryBoardService {
	List<MemberVO> getMemberList(String email) throws Exception;

	int insertCategoryBoard(Map<String, Object> param);

	int insertCategoryBoardUser(Map<String, Object> param);

	int deleteCategoryBoard(String categoryNo) throws Exception;
}
