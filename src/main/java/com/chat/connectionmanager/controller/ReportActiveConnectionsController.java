package com.chat.connectionmanager.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.connectionmanager.handler.ReportActiveConnectionsHandler;
import com.chat.connectionmanager.model.ReportActiveConnectionsRequest;
import com.chat.connectionmanager.model.ReportActiveConnectionsResponse;

@RestController
public class ReportActiveConnectionsController {
	@Inject
	private ReportActiveConnectionsHandler reportActiveConnectionsHandler;

	@RequestMapping("/reportActiveConnections")
	public @ResponseBody ReportActiveConnectionsResponse reportActiveConnections(
			@RequestBody ReportActiveConnectionsRequest reportActiveConnectionsRequest) {
		reportActiveConnectionsHandler.handleReportActiveConnections(reportActiveConnectionsRequest.getUserIds(),
																	 reportActiveConnectionsRequest.getServerDetails());
		return new ReportActiveConnectionsResponse(true);
	}
}
