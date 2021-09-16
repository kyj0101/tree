package com.vtex.tree.space.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface SpaceMapper {

	List<MemberVO> getMemberList(Map<String, Object> param);

}
