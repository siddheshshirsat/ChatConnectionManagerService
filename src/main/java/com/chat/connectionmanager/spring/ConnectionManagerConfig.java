package com.chat.connectionmanager.spring;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.connectionmanager.model.ServerDetails;

@Configuration
public class ConnectionManagerConfig {
	@Bean(name="requestHashFunction")
	public Function<String, Integer> geRequestHashFunction() {
		return requestString -> Math.abs(requestString.hashCode());
	}

	@Bean(name="serverHashFunctions")
	public List<Function<ServerDetails, Integer>> geServerHashFunctions() {
		Function<ServerDetails, Integer> h1 = serverDetails -> Math.abs(serverDetails.getServerId().hashCode()); 
		return Arrays.<Function<ServerDetails, Integer>> asList(h1);
	}

	@Bean
	public TreeMap<Integer, ServerDetails> getConistentHashingRing() {
		return new TreeMap<>();
	}
}
