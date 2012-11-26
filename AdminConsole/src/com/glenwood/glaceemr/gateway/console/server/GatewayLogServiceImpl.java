package com.glenwood.glaceemr.gateway.console.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.glenwood.glaceemr.gateway.console.client.GatewayLogService;
import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.glenwood.glaceemr.gateway.utils.GatewayLogDAO;
import com.glenwood.glaceemr.utils.DataBaseUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * @author gsivashanmugam
 */

public class GatewayLogServiceImpl extends RemoteServiceServlet implements GatewayLogService {
    @Override
    public List<GatewayLog> getList(String fromDate, String toDate, int offSet, int limit) {
    	DataBaseUtils dbUtils = null;
    	try{
    		dbUtils = new DataBaseUtils("java:comp/env/jdbc/gateway");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	GatewayLogDAO logDAO = new GatewayLogDAO();
    	ArrayList<GatewayLog> list = new ArrayList<GatewayLog>();
    	try{
    		list = logDAO.getLog(dbUtils, fromDate, toDate, offSet, limit);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return list;
    }

	@Override
	public List<Object> getInitialList(String fromDate, String toDate, int limit) {
		DataBaseUtils dbUtils = null;
    	try{
    		dbUtils = new DataBaseUtils("java:comp/env/jdbc/gateway");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	GatewayLogDAO logDAO = new GatewayLogDAO();
    	ArrayList<Object> resultObj = new ArrayList<Object>();
    	ArrayList<GatewayLog> list = new ArrayList<GatewayLog>();
    	try{
    		int count = logDAO.getLogCount(dbUtils, fromDate, toDate);
    		list = logDAO.getLog(dbUtils, fromDate, toDate, 0, limit);
    		resultObj.add(0,new Integer(count));
    		resultObj.add(1,list);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return resultObj;
	}
	
	
	public String getGatewayXMLContent(String fileName)throws Exception{
        StringBuilder content = new StringBuilder();
        BufferedReader gatewayin = null;
		try{
			String rootPath = this.getServletContext().getInitParameter("sharedPath")+"/log";
			FileInputStream inStream = new FileInputStream(new File(rootPath+"/2012/11/7/11072012171124_request.txt"));
	        gatewayin = new BufferedReader(new InputStreamReader(inStream));
	        String line;
	        while ((line = gatewayin.readLine()) != null) {
	            content.append(line+"\r\n");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			gatewayin.close();
		}
		return content.toString();
	}
	
}
