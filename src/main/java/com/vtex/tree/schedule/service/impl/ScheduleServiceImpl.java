package com.vtex.tree.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.schedule.mapper.ScheduleMapper;
import com.vtex.tree.schedule.service.ScheduleService;
import com.vtex.tree.schedule.vo.ScheduleVO;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	ScheduleMapper scheduleMapper;
	
	@Override
	public int insertSchedule(ScheduleVO schedule) throws Exception {
		return scheduleMapper.insertSchedule(schedule);
	}

	@Override
	public List<ScheduleVO> getScheduleList(String projectId) throws Exception {
		return scheduleMapper.getScheduleList(projectId);
	}

	@Override
	public int updateSchedule(ScheduleVO schedule) throws Exception {
		return scheduleMapper.updateSchedule(schedule);
	}
	
	@Override
	public int deleteSchedule(String id) throws Exception {
		return scheduleMapper.deleteSchedule(id);
	}

}
