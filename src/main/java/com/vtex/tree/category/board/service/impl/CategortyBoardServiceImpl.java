package com.vtex.tree.category.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.board.mapper.CategoryBoardMapper;
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class CategortyBoardServiceImpl implements CategoryBoardService{
	
	@Autowired
	private CategoryBoardMapper categoryBoardMapper;
	
	@Override
	public List<MemberVO> getMemberList() throws Exception {
		return categoryBoardMapper.getMemberList();
	}
	
	@Override
	public int insertCategoryBoard(Map<String, Object> param) {
		return categoryBoardMapper.insertCategoryBoard(param);
	}
	
	@Override
	public int insertCategoryBoardUser(Map<String, Object> param) {
		return categoryBoardMapper.insertCategoryBoardUser(param);
	}
	
	@Override
	public int deleteCategoryBoard(String categoryNo) throws Exception{
		return categoryBoardMapper.deleteCategoryBoard(categoryNo);
	}

}
