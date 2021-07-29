package com.vtex.tree.commoncode.service;

import java.util.Map;

public interface CommonCodeService {

	void insertCommonCode(Map<String, String> param);

	boolean codeDuplicationCheck(String code);

}
