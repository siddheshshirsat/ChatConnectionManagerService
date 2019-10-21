package com.chat.connectionmanager.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.connectionmanager.handler.SendMessageHandler;
import com.chat.connectionmanager.model.SendMessageRequest;
import com.chat.connectionmanager.model.SendMessageResponse;

@RestController
public class SendMessageController {
	@Inject
	private SendMessageHandler sendMessageHandler;

	@RequestMapping("/sendMessage")
	public @ResponseBody SendMessageResponse sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
		boolean isDelivered = false;
		try {
			isDelivered = sendMessageHandler.sendMessage(sendMessageRequest.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new SendMessageResponse(isDelivered);
	}
}
