package com.vtex.tree.commoncode.service;

import java.util.List;
import java.util.Map;

public interface CommonCodeService {

	void insertCommonCode(Map<String, String> param);

	boolean codeDuplicationCheck(String code);

	int getTotalCommonCode();

	List<Map<String, String>> selectCommonCodeList(Map<String, Object> param);

}
