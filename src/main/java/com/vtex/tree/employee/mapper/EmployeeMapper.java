package com.vtex.tree.employee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.member.vo.MemberVO;

@Mapper
public interface EmployeeMapper {
	
	List<MemberVO> getMemberList(RowBounds rowBounds) throws Exception;
	
	int getMemberListCnt() throws Exception;
	
	MemberVO getMemberDetail(String email) throws Exception;

}
