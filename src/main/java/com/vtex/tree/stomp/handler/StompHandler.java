package com.vtex.tree.stomp.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class StompHandler extends ChannelInterceptorAdapter{

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        System.out.println();
        switch (accessor.getCommand()) {
            case CONNECT:
            	System.out.println("=========== 유저가 Websocket으로 connect()를 한 뒤 호출됨 ==============");
                break;
            case DISCONNECT:
                System.out.println("유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등");
                break;
            case SUBSCRIBE:
            	System.out.println("========  SUBSCRIBE ==========");
            	break;
            case UNSUBSCRIBE:
            	System.out.println("========  UNSUBSCRIBE ==========");
            	break;
            default:
                break;
        }
	}
	
	

}
