package com.vtex.tree.category.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.category.mapper.CategoryMapper;
import com.vtex.tree.category.service.CategoryService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public List<MemberVO> getMemberList(Map<String, Object> param) throws Exception {
		return categoryMapper.getMemberList(param);
	}
}
