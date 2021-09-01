package com.vtex.tree.category.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface CategoryMapper {

	List<MemberVO> getMemberList(Map<String, Object> param);

}
