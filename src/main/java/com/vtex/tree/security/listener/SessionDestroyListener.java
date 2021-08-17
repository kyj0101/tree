package com.vtex.tree.security.listener;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionDestroyListener implements ApplicationListener<SessionDestroyedEvent>{

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		List<SecurityContext> securityContexts = event.getSecurityContexts();

        for (SecurityContext securityContext : securityContexts) {
        	System.out.println(securityContext.toString());
        }

	}

}
