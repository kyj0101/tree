package com.vtex.tree.space.vo;

import java.sql.Date;

import com.vtex.tree.chat.room.vo.ChatRoomVO;

import groovy.transform.ToString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class SpaceVO {
	
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	private String projectId;
}
