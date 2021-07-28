package com.vtex.tree.commoncode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.commoncode.mapper.CommonCodeMapper;
import com.vtex.tree.commoncode.service.CommonCodeService;


@Service
public class CommonCodeServiceImpl implements CommonCodeService{

	@Autowired
	private CommonCodeMapper commonCodeMapper;
	
}
