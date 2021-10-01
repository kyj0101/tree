package com.vtex.tree.chat.vo;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatVO {
	
	private String chatRoomNumber;
	private String chatRoomName;
	private String name;
	private String email;
	private String time;
	private String content;
	private String chatType;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
}
