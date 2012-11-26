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
	public List<GatewayLog> getList(String fromDate, String toDate, int offSet, int limit);
	public List<Object> getInitialList(String fromDate, String toDate, int limit);
	public String getGatewayXMLContent(String fileName)throws Exception;
}
