package com.vtex.tree.socket.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.vtex.tree.member.vo.MemberVO;


@Component
public class SocketHandler extends TextWebSocketHandler{

	public static List<MemberVO> loginMemberList = new ArrayList<>();
	
	public static List<WebSocketSession> sessionList = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
		
		Principal p =  session.getPrincipal();
		MemberVO member = null;
		
		if(p instanceof UsernamePasswordAuthenticationToken) {
			
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
			member = (MemberVO) token.getPrincipal();
		
		}else if(p instanceof OAuth2AuthenticationToken) {
			
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) p;
			DefaultOAuth2User oAuthUser =  (DefaultOAuth2User) token.getPrincipal();
			
			Map<String, Object> attributes  = oAuthUser.getAttributes();
			
			member = new MemberVO();
			member.setEmail((String)attributes.get("email"));
			member.setName((String)attributes.get("name"));
			member.setEsntlId((String)attributes.get("esntlId"));			
		}
		
		loginMemberList.add(member);
		sessionList.add(session);
		
		System.out.println(loginMemberList.toString());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		Principal p =  session.getPrincipal();
		MemberVO member = (MemberVO) null;

		if(p instanceof UsernamePasswordAuthenticationToken) {
			
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
			member = (MemberVO) token.getPrincipal();
		
		}else if(p instanceof OAuth2AuthenticationToken) {
			
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) p;
			DefaultOAuth2User oAuthUser =  (DefaultOAuth2User) token.getPrincipal();
			
			Map<String, Object> attributes  = oAuthUser.getAttributes();
			
			member = new MemberVO();
			member.setEmail((String)attributes.get("email"));
			member.setName((String)attributes.get("name"));
			member.setEsntlId((String)attributes.get("esntlId"));			
		}

		loginMemberList.remove(member);		
		sessionList.remove(session);
		
		System.out.println(loginMemberList.toString());
	}

}
