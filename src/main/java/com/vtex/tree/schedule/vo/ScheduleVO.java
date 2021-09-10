package com.vtex.tree.schedule.vo;

import java.sql.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class ScheduleVO {

	private String id;
	private String projectId;
	private String title;
	private String start;
	private String end;
	private String allDay;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	private String color;
}
