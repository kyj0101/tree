package com.vtex.tree.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.schedule.vo.ScheduleVO;

@Mapper
public interface ScheduleMapper {

	int insertSchedule(ScheduleVO schedule) throws Exception;

	List<ScheduleVO> getScheduleList(String projectId) throws Exception;

	int updateSchedule(ScheduleVO schedule) throws Exception;

	int deleteSchedule(String id) throws Exception;

}
