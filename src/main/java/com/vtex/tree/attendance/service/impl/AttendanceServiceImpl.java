
package com.vtex.tree.attendance.service.impl;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.attendance.mapper.AttendanceMapper;
import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.attendance.vo.AttendanceVO;
import com.vtex.tree.member.vo.Member;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceMapper attendanceMapper;
	
	public int insertIn(AttendanceVO attendance) throws Exception{
		return attendanceMapper.insertIn(attendance);
	}
	
	@Override
	public int isIn(String email) throws Exception {
		return attendanceMapper.isIn(email);
	}
	
	@Override
	public int isOut(String email) throws Exception {
		return attendanceMapper.isOut(email);
	}
	
	@Override
	public int updateOut(Map<String, Object> param) throws Exception {
		return attendanceMapper.updateOut(param);
	}
		
	@Override
	public List<AttendanceVO> getAttendanceList(Map<String, Object> param, RowBounds rowBounds) throws Exception {
		return attendanceMapper.getAttendanceList(param, rowBounds);
	}
	
	@Override
	public int getAttendanceListCnt(Map<String, Object> param) throws Exception {
		return attendanceMapper.getAttendanceListCnt(param);
	}

	@Override
	public int updateAttendance(Map<String, Object> param) throws Exception {
		return attendanceMapper.updateAttendance(param);
	}
	
	@Override
	public List<Member> autoName(String searchName) throws Exception {
		return attendanceMapper.autoName(searchName);
	}
	
	@Override
	public int insertAttendance(Map<String, Object> param) throws Exception {
		return attendanceMapper.insertAttendance(param);
	}
	
	@Override
	public int deleteAttendance(Map<String, Object> param) throws Exception {
		return attendanceMapper.deleteAttendance(param);
	}
}
