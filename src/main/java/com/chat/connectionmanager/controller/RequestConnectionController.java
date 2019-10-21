package com.chat.connectionmanager.controller;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.connectionmanager.handler.ConnectionRequestHandler;
import com.chat.connectionmanager.model.ConnectionRequest;
import com.chat.connectionmanager.model.ConnectionResponse;
import com.chat.connectionmanager.model.GrantStatus;

@RestController
public class RequestConnectionController {
	@Inject
	private ConnectionRequestHandler connecitonRequestHandler;

	@RequestMapping("/requestConnection")
	public @ResponseBody ConnectionResponse requestConnection(@RequestBody ConnectionRequest connectionRequest) {
		Optional<String> connectionHandlerResponse = connecitonRequestHandler
				.handleConnectionRequest(connectionRequest.getUserId());

		return connectionHandlerResponse.map(r -> new ConnectionResponse(GrantStatus.GRANTED, r))
										.orElse(new ConnectionResponse(GrantStatus.DENIED, ""));
	}
}