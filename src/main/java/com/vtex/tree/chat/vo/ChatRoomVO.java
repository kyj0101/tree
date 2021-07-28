package com.vtex.tree.chat.vo;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomVO {
	
	private int chatRoomNUmber;
	private String chatRoomTitle;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
}
