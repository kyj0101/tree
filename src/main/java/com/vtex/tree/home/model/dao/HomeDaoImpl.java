package com.vtex.tree.home.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HomeDaoImpl implements HomeDao {
	
	@Autowired
	private SqlSession session;

	@Override
	public List<String> getMemberList() {
		return session.selectList("sample.test");
	}
}
