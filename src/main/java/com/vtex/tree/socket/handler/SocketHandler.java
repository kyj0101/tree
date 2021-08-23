package com.vtex.tree.socket.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
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
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
		MemberVO member = (MemberVO) token.getPrincipal();
		
		loginMemberList.add(member);
		sessionList.add(session);
		
		System.out.println("확인 확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인");
		System.out.println(loginMemberList.toString());

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Principal p =  session.getPrincipal();
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
		MemberVO member = (MemberVO) token.getPrincipal();
		
		loginMemberList.remove(member);		
		sessionList.remove(session);
		
		System.out.println(loginMemberList.toString());

	}

}
