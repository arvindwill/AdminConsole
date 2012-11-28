package com.glenwood.glaceemr.gateway.console.client;

import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 *
 * @author gsivashanmugam
 */

public interface GatewayLogServiceAsync {
    void getList(String fromDate, String toDate, int offSet, int limit, AsyncCallback<List<GatewayLog>> callBack);
	void getInitialCount(String fromDate, String toDate, AsyncCallback<Integer> callback);
	void getGatewayXMLContent(String requestFileName,String responseFileName,AsyncCallback<List<String>> callback)throws Exception;
}