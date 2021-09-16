package com.vtex.tree.category.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.mapper.CategoryMapper;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class CategortyServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public List<MemberVO> getMemberList(String email) throws Exception {
		return categoryMapper.getMemberList(email);
	}
	
	@Override
	public int insertCategoryBoard(Map<String, Object> param) {
		return categoryMapper.insertCategoryBoard(param);
	}
	
	@Override
	public int insertCategoryBoardMember(Map<String, Object> param) {
		return categoryMapper.insertCategoryBoardMember(param);
	}
	
	@Override
	public List<Map<String, Object>> getCategoryList(String email) {
		return categoryMapper.getCategoryList(email);
	}
	
	@Override
	public Map<String, Object> getCategory(int category) {
		return categoryMapper.getCategory(category);
	}
	
	@Override
	public int deleteCategoryBoard(Map<String, Object> param) throws Exception{
		return categoryMapper.deleteCategoryBoard(param);
	}
	
	@Override
	public int outBoard(Map<String, Object> param) throws Exception {
		return categoryMapper.outBoard(param);
	}
	
	@Override
	public List<MemberVO> getMemberListToInvite(String categoryNo) throws Exception {
		return categoryMapper.getMemberListToInvite(categoryNo);
	}

}
