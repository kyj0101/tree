package com.vtex.tree.commoncode.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

public interface CommonCodeService {

	int insertCommonCode(Map<String, String> param) throws Exception;

	int codeDuplicationCheck(String code);

	int getTotalCommonCode();

	List<Map<String, String>> selectCommonCodeList(Map<String, Object> param, RowBounds rowBounds);

	int updateCommonCode(Map<String, String> param) throws Exception;

	int insertDetailCode(Map<String, String> param) throws Exception;

	List<Map<String, Object>> selectDetailCode(Map<String, Object> param, RowBounds rowBounds);

	int deleteCommonCode(Map<String, String> param) throws Exception;

	int getTotalDetailCode(String code);

	Map<String, Object> selectOneDetailCode(String detailCode);

	int updateDetailCode(Map<String, String> param) throws Exception;

	Map<String, String> selectOneCommonCode(String code);

	int detailCodeDuplicationCheck(String code);

	int deleteDetailCode(Map<String, String> param) throws Exception;
	
	List<Map<String, String>> selectCmmnCodeList(Map<String, String> param) throws Exception;

	int countDetailCode(Map<String, String> param);


}
