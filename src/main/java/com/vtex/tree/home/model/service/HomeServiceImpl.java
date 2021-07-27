package com.vtex.tree.home.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.home.model.dao.HomeDao;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private HomeDao homeDao;

	@Override
	public List<String> getMemberList() {
		return homeDao.getMemberList();
	}
	
}
