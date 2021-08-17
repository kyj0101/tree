package com.vtex.tree.config.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.vtex.tree.stomp.handler.StompHandler;
import com.vtex.tree.stomp.handler.StompLoginHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	@Autowired
	private StompHandler stompHandler;
	
	@Autowired
	private StompLoginHandler stompLoginHandler;
	
/**
  * stomp로 접속한 경우의 connectEndPoint설정: client단의 `Stomp.over(socket)`에 대응함.
  * 
  * 내부적으로 SockJS를 통해, websocket, xhr-streaming, xhr-polling 중에 가장 적합한 transport를 찾음.
  */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/stomp")
              .withSockJS()
              .setInterceptors(new HttpSessionHandshakeInterceptor());//이 인터셉터를 통해 HttpSession객체에 접근할 수 있다.
      
  }

  /**
		 * destination header에 따라 다음과 같이 처리된다.
		 * 1. /app/hello 와 같이 prefix로 시작된다면, @MessageMapping에 의해 처리.
		 * 2. /notice/abcde 와 같이 simpleBroker에 등록된 prefix로 시작하는 요청은 직접 subscriber에게 전달
		 * 
		 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 1. prefix시작되는 요청은 @Controller의 핸들러메소드 @MessageMapping messageHandler로 전달
		// MessageBrokerRegistry
		// org.springframework.messaging.simp.config.MessageBrokerRegistry.setApplicationDestinationPrefixes(String...
		// prefixes)
		// registry.setApplicationDestinationPrefixes("/app");

		// 2. 다음 prefix로 시작되는 요청은 simpleBroker에 의해 직접 subscriber에게 전달.
		registry.enableSimpleBroker("/chat/active");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		/*
		 * registration.interceptors(stompHandler);
		 * registration.interceptors(stompLoginHandler);
		 */
	}
}
