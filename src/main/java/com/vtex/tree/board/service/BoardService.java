package com.vtex.tree.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.member.vo.Member;

public interface BoardService {
	int insertBoard(Map<String, Object> param) throws Exception;

	int insertFile(Map<String, Object> map) throws Exception;

	int insertFileDetail(Map<String, Object> fileMap) throws Exception;

	List<BoardVO> getBoardList(int category, RowBounds rowBounds) throws Exception;
	
	int getBoardListCnt(int category) throws Exception;
	
	BoardVO selectOneBoard(int boardNo) throws Exception;
	
	List<Map<String, Object>> selectBoardFiles(int fileId) throws Exception;

	void addViewNum(Map<String, Object> param) throws Exception;
	

	int getMaxFileSn(int fileId) throws Exception;
	
	int updateBoard(Map<String, Object> param) throws Exception;

	int updateInsertFile(Map<String, Object> param);

	int deleteFile(Map<String, Object> param) throws Exception;

	int deleteBoard(String boardNo) throws Exception;

	Map<String, Object> getFile(Map<String, Object> param) throws Exception;

	List<Member> getBoardMemberList(String categoryNo) throws Exception;
}
