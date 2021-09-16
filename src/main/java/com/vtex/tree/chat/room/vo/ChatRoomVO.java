package com.vtex.tree.chat.room.vo;

import java.sql.Date;

import com.vtex.tree.space.vo.SpaceVO;

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
public class ChatRoomVO extends SpaceVO{
	
	private int chatRoomNumber;
	private String chatRoomTitle;
}
