package com.vtex.tree.socket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vtex.tree.videocall.vo.CallSessionVO;

@Component
public class WebRTCHandler extends TextWebSocketHandler{
	
	private List<WebSocketSession> sessionList = new ArrayList<>();
	
	private List<CallSessionVO> callSessionList = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
		sessionList.add(session);
		
		System.out.println("add====================");
		System.out.println(sessionList.toString());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		
		System.out.println("remove===================");
		System.out.println(sessionList.toString());
	}	
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		TextMessage text = (TextMessage) message;
		System.out.println(text.getPayload());
		
		Map<String, Object> msgMap = new ObjectMapper().readValue(text.getPayload(), Map.class);
		
		String type = (String) msgMap.get("type");
		String videoCallId = (String) msgMap.get("videoCallId");

		switch (type) {
			case "create":
				CallSessionVO callSessionHost = new CallSessionVO(videoCallId, session);
				callSessionList.add(callSessionHost);
				return;
			
			case "join":
				CallSessionVO callSessionJoin = new CallSessionVO(videoCallId, session);
				callSessionList.add(callSessionJoin);
				
				Map<String, Object> tmp = new HashMap<>();
				tmp.put("type", "join");
				JSONObject jsonObj = new JSONObject(tmp);
				
				text = new TextMessage(jsonObj.toJSONString());
				break;
				
			case "offer":
				break;
			case "answer":
				break;
			case "ice":
				break;
		}
		
		try {
			System.out.println(callSessionList.toString());
			WebSocketSession sendSession = findWebSocketSession(videoCallId, session);
			System.out.println(sendSession);
			sendSession.sendMessage(text);	
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		super.handleMessage(session, message);
	}
	
	private WebSocketSession findWebSocketSession(String videoCallId, WebSocketSession presentSession) {
		
		for(CallSessionVO callSession : callSessionList) {
			
			WebSocketSession session = callSession.getSession();
			
			if(videoCallId.equals(callSession.getVideoCallId()) && session != presentSession) {
				return session;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
