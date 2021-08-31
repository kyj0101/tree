package com.vtex.tree.socket.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {
	@EventListener
	  public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
	      GenericMessage message = (GenericMessage) event.getMessage();
	      String simpDestination = (String) message.getHeaders().get("simpDestination");
	      System.out.println("==========================================================================");
	  }

}