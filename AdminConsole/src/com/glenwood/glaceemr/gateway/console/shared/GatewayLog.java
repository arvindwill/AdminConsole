package com.glenwood.glaceemr.gateway.console.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GatewayLog implements IsSerializable {
	int logId;
	String timeStamp;
	String endPoint;
	String entityName;
	String accountId;
	String userId;
	String requestIP;
	String requestFileName;
	String responseFileName;
	
	public String getRequestFileName() {
		return requestFileName;
	}

	public void setRequestFileName(String requestFileName) {
		this.requestFileName = requestFileName;
	}

	public String getResponseFileName() {
		return responseFileName;
	}

	public void setResponseFileName(String responseFileName) {
		this.responseFileName = responseFileName;
	}

	public GatewayLog(){
	}
	
	public int getLogId() {
		return logId;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getRequestIP() {
		return requestIP;
	}
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
