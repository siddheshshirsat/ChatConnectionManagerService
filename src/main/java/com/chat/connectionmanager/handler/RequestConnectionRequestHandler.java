package com.chat.connectionmanager.handler;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.chat.connectionmanager.model.ServerDetails;

@Component
public class RequestConnectionRequestHandler {
	@Inject
	private ConnectionLoadManager connectionLoadManager;
	
	public Optional<String> handleRequestConnection(String userId) {
		if(userId == null || userId.length() == 0 ) {
			throw new IllegalArgumentException();
		}
		return connectionLoadManager.getServerDetails(userId)
									.map(ServerDetails::getEndpoint);
	}
}
