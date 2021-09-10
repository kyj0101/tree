package com.vtex.tree.schedule.service;

import java.util.List;

import com.vtex.tree.schedule.vo.ScheduleVO;

public interface ScheduleService {

	List<ScheduleVO> getScheduleList(String projectId) throws Exception;

	int insertSchedule(ScheduleVO schedule) throws Exception;

	int updateSchedule(ScheduleVO schedule) throws Exception;

	int deleteSchedule(String id) throws Exception;


}
