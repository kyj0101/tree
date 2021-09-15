package com.vtex.tree.videocall.vo;


import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class CallSessionVO {
	
	private String videoCallId;
	private WebSocketSession session;
}
