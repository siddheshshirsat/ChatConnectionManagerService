package com.chat.connectionmanager.controller;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.connectionmanager.handler.RequestConnectionRequestHandler;
import com.chat.connectionmanager.model.GrantStatus;
import com.chat.connectionmanager.model.RequestConnectionRequest;
import com.chat.connectionmanager.model.RequestConnectionResponse;

@RestController
public class RequestConnectionController {
	@Inject
	private RequestConnectionRequestHandler requestConnectionRequestHandler;

	@RequestMapping("/requestConnection")
	public @ResponseBody RequestConnectionResponse requestConnection(@RequestBody RequestConnectionRequest requestConnectionRequest) {
		Optional<String> requestConnectionHandlerResponse = requestConnectionRequestHandler.handleRequestConnection(requestConnectionRequest.getUserId());

		return requestConnectionHandlerResponse.map(r -> new RequestConnectionResponse(GrantStatus.GRANTED, r))
				.orElse(new RequestConnectionResponse(GrantStatus.DENIED, ""));
	}
}
