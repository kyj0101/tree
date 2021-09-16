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

	private List<CallSessionVO> callSessionList = new ArrayList<>();
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		TextMessage text = (TextMessage) message;

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
				
				Map<String, Object> joinMap = new HashMap<>();
				joinMap.put("type", "join");
				JSONObject joinObj = new JSONObject(joinMap);
				
				text = new TextMessage(joinObj.toJSONString());
				break;
			
			case "hangup":
				Map<String, Object> hangUpMap = new HashMap<>();
				hangUpMap.put("type", "hangUp");
				JSONObject hangUpObj = new JSONObject(hangUpMap);
				
				text = new TextMessage(hangUpObj.toJSONString());
				break;
				
			case "offer":
				break;
			case "answer":
				break;
			case "ice":
				break;
		}
		
		try {
			WebSocketSession sendSession = findWebSocketSession(videoCallId, session);
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
