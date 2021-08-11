package com.vtex.tree.member.service;

import java.util.List;
import java.util.Map;

public interface MemberService {

	List<String> getDepartmentList();

	List<String> getPositionList();

	void updateMember(Map<String, Object> param);

	void updatePassword(Map<String, String> param);

	int withdraw(Map<String, Object> param) throws Exception;

}
