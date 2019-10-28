package com.chat.connectionmanager.handler;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.chat.connectionmanager.model.ServerDetails;
import com.chat.connectionmanager.state.ActiveConnections;

@Component
public class ReportActiveConnectionsHandler {

	@Inject
	private ActiveConnections activeConnections;

	public void handleReportActiveConnections(Set<String> userIds, ServerDetails serverDetails) {
		userIds.stream().forEach(userId -> activeConnections.addConnection(userId, serverDetails));
	}
}
