package com.chat.connectionmanager.handler;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class ConnectionRequestHandler {
	@Inject
	private ConnectionLoadManager connectionLoadManager;
	
	public Optional<String> handleConnectionRequest(String userId) {
		if(userId == null || userId.length() == 0 ) {
			throw new IllegalArgumentException();
		}
		return connectionLoadManager.getUrl(userId);
	}
}
