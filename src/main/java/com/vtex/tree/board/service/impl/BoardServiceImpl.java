package com.vtex.tree.board.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.board.mapper.BoardMapper;
import com.vtex.tree.board.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public int insertBoard(Map<String, Object> param) throws Exception {
		return boardMapper.insertBoard(param);
	}

	@Override
	public int insertFile(Map<String, Object> map) throws Exception {
		return boardMapper.insertFile(map);
	}

	@Override
	public int insertFileDetail(Map<String, Object> fileMap) throws Exception {
		return boardMapper.insertFileDetail(fileMap);
	}
}
