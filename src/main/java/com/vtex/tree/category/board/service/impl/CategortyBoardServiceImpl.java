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
	public List<MemberVO> getMemberList(String email) throws Exception {
		return categoryBoardMapper.getMemberList(email);
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
	public List<Map<String, Object>> getCategoryList(String email) {
		return categoryBoardMapper.getCategoryList(email);
	}
	
	@Override
	public Map<String, Object> getCategory(int category) {
		return categoryBoardMapper.getCategory(category);
	}
	
	@Override
	public int deleteCategoryBoard(Map<String, Object> param) throws Exception{
		return categoryBoardMapper.deleteCategoryBoard(param);
	}
	
	@Override
	public int outBoard(Map<String, Object> param) throws Exception {
		return categoryBoardMapper.outBoard(param);
	}

}
