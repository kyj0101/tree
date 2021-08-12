package com.vtex.tree.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.board.vo.BoardVO;

@Mapper
public interface BoardMapper {
	int insertBoard(Map<String, Object> param) throws Exception;

	int insertFile(Map<String, Object> map) throws Exception;

	int insertFileDetail(Map<String, Object> fileMap) throws Exception;

	List<BoardVO> getBoardList(int category, RowBounds rowBounds) throws Exception;
	
	int getBoardListCnt(int category) throws Exception;
	
	BoardVO selectOneBoard(int boardNo) throws Exception;
	
	List<Map<String, Object>> selectBoardFiles(int fileId) throws Exception;
	
	void addViewNum(Map<String, Object> param) throws Exception;
	
	int deleteFile(Map<String, Object> param) throws Exception;
	
	String getMaxFileSn(int fileId) throws Exception;
	
	int updateBoard(Map<String, Object> param) throws Exception;
	
	int deleteBoard(String boardNo) throws Exception;
	
	List<Map<String, Object>> getCategoryList(String email);
	
	Map<String, Object> getCategory(int category);
	
	int updateInsertFile(Map<String, Object> param);
	
	Map<String, Object> getFile(Map<String, Object> param) throws Exception;
}
