package com.chat.connectionmanager.state;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.chat.connectionmanager.model.ServerDetails;

import lombok.ToString;

@Component
@ToString
public class ActiveConnections {
	private Map<String, ServerDetails> activeConnectionsMap;

	@Inject
	public ActiveConnections() {
		activeConnectionsMap = new ConcurrentHashMap<>();
	}

	public void addConnection(String key, ServerDetails serverDetails) {
		activeConnectionsMap.put(key, serverDetails);
	}

	public Optional<ServerDetails> get(String recipientId) {
		return Optional.ofNullable(activeConnectionsMap.get(recipientId));
	}
}
