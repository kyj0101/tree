package com.vtex.tree.room.meeting.vo;

import java.sql.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class MeetingRoomVO {
	
	private String meetingRoomNumber;
	private String projectId;
	private String host;
	private String guest;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	
}
