package com.vtex.tree.stomp.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.vtex.tree.member.vo.MemberVO;

@Component
public class SocketHandler extends TextWebSocketHandler{

	public static List<MemberVO> loginMemberList = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Principal p =  session.getPrincipal();
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
		MemberVO member = (MemberVO) token.getPrincipal();
		
		loginMemberList.add(member);
		
		System.out.println(loginMemberList.toString());

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Principal p =  session.getPrincipal();
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)p;
		MemberVO member = (MemberVO) token.getPrincipal();
		
		loginMemberList.remove(member);
		System.out.println(loginMemberList.toString());
		
	}

}
