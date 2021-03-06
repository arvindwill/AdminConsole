package com.glenwood.glaceemr.gateway.console.client;

import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 *
 * @author gsivashanmugam
 */

@RemoteServiceRelativePath("GatewayLogService")
public interface GatewayLogService extends RemoteService {
	public List<GatewayLog> getList(String fromDate, String toDate, int offSet, int limit) throws Exception;
	public Integer getInitialCount(String fromDate, String toDate) throws Exception;
	List<String> getGatewayXMLContent(String requestFileName,String responseFileName) throws Exception;
}
