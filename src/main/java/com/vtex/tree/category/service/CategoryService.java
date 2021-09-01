package com.vtex.tree.category.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface CategoryService {

	List<MemberVO> getMemberList(Map<String, Object> param) throws Exception;

}
