package com.vtex.tree.chat.mapper.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vtex.tree.chat.mapper.ChatMapper;

@Repository
public class ChatMapperImpl implements ChatMapper {
	
	@Autowired
	private SqlSession session;
}
