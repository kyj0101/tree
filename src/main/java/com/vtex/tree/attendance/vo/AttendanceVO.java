package com.vtex.tree.attendance.vo;

import java.sql.Date;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceVO {
	private String email;
	private String day;
	private String inTime;
	private String outTime;
	private String latenessAt;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	private String name;
	private String latenessReason;
}
