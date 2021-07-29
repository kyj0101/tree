package com.vtex.tree.commoncode.mapper;

import java.util.Map;

public interface CommonCodeMapper {

	void insertCommonCode(Map<String, String> param);

	boolean codeDuplicationCheck(String code);

}
