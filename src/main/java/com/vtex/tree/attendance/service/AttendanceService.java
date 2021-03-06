package com.vtex.tree.attendance.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.vtex.tree.attendance.vo.AttendanceVO;
import com.vtex.tree.member.vo.Member;

public interface AttendanceService {

	int insertIn(AttendanceVO attendance) throws Exception;

	int isIn(String email) throws Exception;

	int updateOut(Map<String, Object> param) throws Exception;

	int isOut(String email) throws Exception;

	List<AttendanceVO> getAttendanceList(Map<String, Object> param, RowBounds rowBounds) throws Exception;

	int getAttendanceListCnt(Map<String, Object> param)throws Exception;

	int updateAttendance(Map<String, Object> param) throws Exception;

	List<Member> autoName(String searchName) throws Exception;

	int insertAttendance(Map<String, Object> param) throws Exception;

	int deleteAttendance(Map<String, Object> param) throws Exception;

}
