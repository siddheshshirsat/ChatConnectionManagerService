package com.chat.connectionmanager.handler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import com.chat.connectionmanager.model.ServerDetails;

@Component
public class ConnectionLoadManager {
	private static int RING_LENGTH = 360;

	@Inject
	@Named("serverHashFunctions")
	private List<Function<ServerDetails, Integer>> serverHashFunctions;

	@Inject
	@Named("requestHashFunction")
	private Function<String, Integer> requestHashFunction;

	@Inject
	private TreeMap<Integer, ServerDetails> conistentHashingRing;

	public Optional<String> getUrl(String userId) {
		Integer requestHashValue = requestHashFunction.apply(userId);
		return getNearestServerOnRing(requestHashValue % RING_LENGTH).map(ServerDetails::getUrl);
	}

	public void addServerOnRing(ServerDetails serverDetails) {
		serverHashFunctions.stream().map(h -> h.apply(serverDetails) % RING_LENGTH)
									.forEach(v -> conistentHashingRing.put(v, serverDetails));
	}

	@PostConstruct
	private void postConstruct() {
		addServerOnRing(new ServerDetails("serverId1", "http://localhost:9001"));
	}

	private Optional<ServerDetails> getNearestServerOnRing(int requestHashValue) {
		Map.Entry<Integer, ServerDetails> resultServerDetails = conistentHashingRing.higherEntry(requestHashValue);
		if(resultServerDetails == null) {
			resultServerDetails = conistentHashingRing.lowerEntry(requestHashValue);
		}
		return Optional.ofNullable(resultServerDetails).map(Map.Entry::getValue);
	}
}
