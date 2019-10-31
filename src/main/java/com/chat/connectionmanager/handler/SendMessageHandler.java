package com.chat.connectionmanager.handler;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.chat.connectionmanager.model.Message;
import com.chat.connectionmanager.model.ServerDetails;
import com.chat.connectionmanager.state.ActiveConnections;
import com.chat.pushnotification.model.DeliverMessageRequest;
import com.chat.pushnotification.model.DeliverMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SendMessageHandler {
	private static final String DELIVER_MESSAGE_API = "/deliverMessage";

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private ActiveConnections activeConnections;

	@Inject
	private HttpClient httpClient;

	public boolean handleSendMessage(Message message) throws UnsupportedCharsetException, ClientProtocolException, IOException {
		Optional<ServerDetails> serverDetailsOptional = activeConnections.get(message.getRecipientId());
		System.out.println("Reached1...sending message " + serverDetailsOptional + ", " + message + ", " + activeConnections);

		if(serverDetailsOptional.isPresent()) {
			System.out.println("Reached...sending message " + serverDetailsOptional + ", " + message);
			return sendMessage(serverDetailsOptional.get(), message);
		} else {
			return false;
		}
	}

	private boolean sendMessage(ServerDetails serverDetails, Message message) throws ClientProtocolException, IOException {
		DeliverMessageRequest deliverMessageRequest = new DeliverMessageRequest(message.getSenderId(), message.getRecipientId(), message.getContent(), message.getTimestamp());
		HttpPost post = new HttpPost(serverDetails.getUrl() + DELIVER_MESSAGE_API);

		post.setEntity(new StringEntity(objectMapper.writeValueAsString(deliverMessageRequest), ContentType.APPLICATION_JSON));

		HttpResponse response = httpClient.execute(post);
		DeliverMessageResponse deliverMessageResponse =  objectMapper.readValue(EntityUtils.toString(response.getEntity()), DeliverMessageResponse.class);
		return deliverMessageResponse.isDelivered();
	}
}
