package com.vtex.tree.board.service;

import java.util.Map;

public interface BoardService {
	int insertBoard(Map<String, Object> param) throws Exception;

	int insertFile(Map<String, Object> map) throws Exception;

	int insertFileDetail(Map<String, Object> fileMap) throws Exception;
}
