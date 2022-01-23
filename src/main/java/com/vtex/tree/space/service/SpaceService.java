package com.vtex.tree.space.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.Member;

public interface SpaceService {

	List<Member> getMemberList(Map<String, Object> param) throws Exception;

}
