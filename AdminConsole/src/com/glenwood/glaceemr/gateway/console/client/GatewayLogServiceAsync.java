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
	void getInitialList(String fromDate, String toDate, int limit,AsyncCallback<List<Object>> callback);
	void getGatewayXMLContent(String requestFileName,String responseFileName,AsyncCallback<List<String>> callback)throws Exception;
}