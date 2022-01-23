package com.vtex.tree.socket.handler;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vtex.tree.chat.vo.ChatVO;
import com.vtex.tree.member.vo.Member;


@Component
public class SocketHandler extends TextWebSocketHandler{

	public static List<Member> loginMemberList = new ArrayList<>();
	
	public static List<WebSocketSession> sessionList = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
		
		Principal p =  session.getPrincipal();
		Member member = null;
		
		if(p instanceof UsernamePasswordAuthenticationToken) {
			
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
			member = (Member) token.getPrincipal();
			member.setSessionId(session.getId());
			
		}else if(p instanceof OAuth2AuthenticationToken) {
			
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) p;
			DefaultOAuth2User oAuthUser =  (DefaultOAuth2User) token.getPrincipal();
			
			Map<String, Object> attributes  = oAuthUser.getAttributes();
			
			member = new Member();
			member.setEmail((String)attributes.get("email"));
			member.setName((String)attributes.get("name"));
			member.setEsntlId((String)attributes.get("esntlId"));
			member.setPositionName((String)attributes.get("positionName"));
			member.setDepartmentName((String)attributes.get("departmentName"));
			member.setSessionId(session.getId());
		}

		loginMemberList.remove(member);
		loginMemberList.add(member);
		System.out.println(loginMemberList.toString());
		sessionList.add(session);
		System.out.println(sessionList.toString());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		Principal p =  session.getPrincipal();
		Member member = (Member) null;

		if(p instanceof UsernamePasswordAuthenticationToken) {
			
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
			member = (Member) token.getPrincipal();
		
		}else if(p instanceof OAuth2AuthenticationToken) {
			
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) p;
			DefaultOAuth2User oAuthUser =  (DefaultOAuth2User) token.getPrincipal();
			
			Map<String, Object> attributes  = oAuthUser.getAttributes();
			
			member = new Member();
			member.setEsntlId((String)attributes.get("esntlId"));
		}

		loginMemberList.remove(member);		
		removeSessionListWithSessionId(session.getId());
	}
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		String payload = (String)message.getPayload();
		Map<String, Object> msgMap = new ObjectMapper().readValue(payload, Map.class);
		msgMap.put("type", "call");
		
		String esntlId = (String) msgMap.get("esntlId");
		String sessionId = findSessionIdWithEsntlId(esntlId);
		WebSocketSession s = findWebSocketSessionWithSessionId(sessionId);
		JSONObject jsonObj = new JSONObject(msgMap);
		
		s.sendMessage(new TextMessage(jsonObj.toJSONString()));
	}

	public void sendChatMessage(List<Member> memberList, ChatVO chat) {
		
		Member loginMember = new Member();
		
		loginMember.setEsntlId(chat.getEsntlId());
		memberList.remove(loginMember);

		for(Member member : memberList) {

			try {
			
				String sessionId = findSessionIdWithEsntlId(member.getEsntlId()); 
				WebSocketSession session = findWebSocketSessionWithSessionId(sessionId);
				ObjectMapper objMapper = new ObjectMapper();
				Map<String, Object> objMap = objMapper.convertValue(chat, Map.class);
				
				objMap.put("type", "chat");
				session.sendMessage(new TextMessage(objMapper.writeValueAsString(objMap)));					
				
			} catch (IllegalArgumentException e) {
				continue;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String findSessionIdWithEsntlId(String esntlId) {
		
		for(Member m : loginMemberList) {
			
			if(esntlId.equals(m.getEsntlId())) {
				return m.getSessionId();
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	private WebSocketSession findWebSocketSessionWithSessionId(String sessionId) {
		
		for(WebSocketSession s : sessionList) {

			if(sessionId.equals(s.getId())) {
				return s;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	private void removeSessionListWithSessionId(String sessionId) {
		
		List<WebSocketSession> newSessionList = new ArrayList<>();
		
		for(WebSocketSession s : sessionList) {
			
			if(sessionId.equals(s.getId())) {
				continue;
			}
			
			newSessionList.add(s);
		}
		
		sessionList = newSessionList;
	}
}
