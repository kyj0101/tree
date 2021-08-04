package com.vtex.tree.board.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	int insertBoard(Map<String, Object> param) throws Exception;

	int insertFile(Map<String, Object> map) throws Exception;

	int insertFileDetail(Map<String, Object> fileMap) throws Exception;
	
}
