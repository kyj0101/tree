package com.vtex.tree.commoncode.mapper;

import java.util.List;
import java.util.Map;

public interface CommonCodeMapper {

	void insertCommonCode(Map<String, String> param);

	boolean codeDuplicationCheck(String code);

	int getTotalCommonCode();

	List<Map<String, String>> selectCommonCodeList(Map<String, Object> param);

}
