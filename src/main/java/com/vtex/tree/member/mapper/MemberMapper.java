

package com.vtex.tree.member.mapper;

import java.util.List;
import java.util.Map;

public interface MemberMapper {

	List<String> getDepartmentList();

	List<String> getPositionList();

	void updateMember(Map<String, Object> param);

	void updatePassword(Map<String, String> param);

}
