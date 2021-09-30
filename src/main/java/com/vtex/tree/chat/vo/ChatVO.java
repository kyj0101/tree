package com.vtex.tree.chat.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class ChatVO {
	
	private String chatRoomNumber;
	private String name;
	private String email;
	private String time;
	private String content;
}
