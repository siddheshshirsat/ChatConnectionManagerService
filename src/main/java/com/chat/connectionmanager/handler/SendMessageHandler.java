package com.chat.connectionmanager.handler;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.chat.connectionmanager.model.Message;
import com.chat.connectionmanager.model.SendMessageRequest;
import com.chat.connectionmanager.model.SendMessageResponse;
import com.chat.connectionmanager.model.ServerDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SendMessageHandler {
	private static final String SEND_MESSAGE_API = "/sendMessage";

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private ConcurrentHashMap<String, ServerDetails> activeConnections;

	@Inject // 					httpClient = HttpClients.createDefault();
	private HttpClient httpClient;

	public boolean sendMessage(Message message) throws UnsupportedCharsetException, ClientProtocolException, IOException {
		ServerDetails serverDetails = activeConnections.get(message.getRecipientId());

		if(serverDetails != null) {
			SendMessageRequest sendMessageRequest = new SendMessageRequest(message);
			HttpPost post = new HttpPost(serverDetails.getUrl() + SEND_MESSAGE_API);

			post.setEntity(new StringEntity(objectMapper.writeValueAsString(sendMessageRequest), ContentType.APPLICATION_JSON));

			HttpResponse response = httpClient.execute(post);
			SendMessageResponse sendMessageResponse =  objectMapper.readValue(EntityUtils.toString(response.getEntity()), SendMessageResponse.class);
			return sendMessageResponse.isDelivered();
		}
		return false;
	}
}
