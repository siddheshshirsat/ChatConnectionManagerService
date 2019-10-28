package com.chat.connectionmanager.spring;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.connectionmanager.model.ServerDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ConnectionManagerConfig {

	@Bean(name="requestHashFunction")
	public Function<String, Integer> geRequestHashFunction() {
		return requestString -> Math.abs(requestString.hashCode());
	}

	@Bean(name="serverHashFunctions")
	public List<Function<ServerDetails, Integer>> getServerHashFunctions() {
		Function<ServerDetails, Integer> h1 = serverDetails -> Math.abs(serverDetails.getServerId().hashCode()); 
		return Arrays.<Function<ServerDetails, Integer>> asList(h1);
	}

	@Bean
	public TreeMap<Integer, ServerDetails> getConistentHashingRing() {
		return new TreeMap<>();
	}

	@Bean
	public ObjectMapper geObjectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public HttpClient getHttpClient() {
		return HttpClients.createDefault();
	}
}
