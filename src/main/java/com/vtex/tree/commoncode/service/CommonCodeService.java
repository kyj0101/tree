package com.vtex.tree.commoncode.service;

import java.util.List;
import java.util.Map;

public interface CommonCodeService {

	void insertCommonCode(Map<String, String> param);

	boolean codeDuplicationCheck(String code);

	int getTotalCommonCode();

	List<Map<String, String>> selectCommonCodeList(Map<String, Object> param);

	void updateCommonCode(Map<String, String> param);

	void insertDetailCode(Map<String, String> param);

	List<Map<String, Object>> selectDetailCodeList(Map<String, Object> param);

	void deleteCommonCode(Map<String, String> param);

	int getTotalDetailCode(String code);

	Map<String, Object> selectOneDetailCode(String detailCode);


}
