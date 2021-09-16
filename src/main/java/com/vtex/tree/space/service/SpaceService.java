package com.vtex.tree.space.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface SpaceService {

	List<MemberVO> getMemberList(Map<String, Object> param) throws Exception;

}
