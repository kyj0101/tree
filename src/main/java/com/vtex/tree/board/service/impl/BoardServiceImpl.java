package com.vtex.tree.board.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.board.mapper.BoardMapper;
import com.vtex.tree.board.service.BoardService;
import com.vtex.tree.board.vo.BoardVO;

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


	@Override
	public List<BoardVO> getBoardList(int category, RowBounds rowBounds) throws Exception{
		return boardMapper.getBoardList(category, rowBounds);
	}

	@Override
	public int getBoardListCnt(int category) throws Exception {
		return boardMapper.getBoardListCnt(category);
	}
	
	@Override
	public BoardVO selectOneBoard(int boardNo) throws Exception {
		return boardMapper.selectOneBoard(boardNo);
	}
	
	@Override
	public List<Map<String, Object>> selectBoardFiles(int fileId) throws Exception {
		return boardMapper.selectBoardFiles(fileId);
	}
	
	@Override
	public void addViewNum(Map<String, Object> param) throws Exception {
		boardMapper.addViewNum(param);
	}
	
	@Override
	public int deleteFile(Map<String, Object> param) throws Exception {
		return boardMapper.deleteFile(param);
	}
	
	@Override
	public int getMaxFileSn(int fileId) throws Exception {
		return boardMapper.getMaxFileSn(fileId);
	}
	
	@Override
	public int updateBoard(Map<String, Object> param) throws Exception {
		return boardMapper.updateBoard(param);
	}
	
	@Override
	public int deleteBoard(String boardNo) throws Exception {
		return boardMapper.deleteBoard(boardNo);
	}
}
