

package com.vtex.tree.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	List<String> getDepartmentList();

	List<String> getPositionList();

	void updateMember(Map<String, Object> param);
	
	int withdraw(Map<String, Object> param) throws Exception;
}
