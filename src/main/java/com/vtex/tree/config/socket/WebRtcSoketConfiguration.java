package com.vtex.tree.config.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.vtex.tree.socket.handler.SocketHandler;
import com.vtex.tree.socket.handler.WebRTCHandler;

@Configuration
@EnableWebSocket
public class WebRtcSoketConfiguration implements WebSocketConfigurer{
	
	@Autowired
	private WebRTCHandler webRtcHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webRtcHandler, "/rtc");
	}
	
}
