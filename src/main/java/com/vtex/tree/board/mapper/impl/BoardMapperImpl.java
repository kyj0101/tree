package com.vtex.tree.board.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.board.mapper.BoardMapper;

@Repository
public class BoardMapperImpl implements BoardMapper{
	
	@Autowired
	private SqlSession session;
	
}
