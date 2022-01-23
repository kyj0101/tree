package com.vtex.tree.space.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.member.vo.Member;
import com.vtex.tree.space.mapper.SpaceMapper;
import com.vtex.tree.space.service.SpaceService;

@Service
public class SpaceServiceImpl implements SpaceService{
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Override
	public List<Member> getMemberList(Map<String, Object> param) throws Exception {
		return spaceMapper.getMemberList(param);
	}
}
