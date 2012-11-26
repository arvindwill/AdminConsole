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
    		dbUtils = new DataBaseUtils(this.getServletContext().getInitParameter("connectionString"));
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
    		dbUtils = new DataBaseUtils(this.getServletContext().getInitParameter("connectionString"));
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
	
	
	public List<String> getGatewayXMLContent(String requestFileName, String responseFileName)throws Exception{
        StringBuilder content = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();
        BufferedReader bufferedReader = null;
		try{
			String rootPath = this.getServletContext().getInitParameter("sharedPath")+"/log";
			
			FileInputStream requestInStream = new FileInputStream(new File(rootPath+requestFileName));
			bufferedReader = new BufferedReader(new InputStreamReader(requestInStream));
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            content.append(line+"\r\n");
	        }
	        result.add(content.toString());
	        requestInStream.close();

			FileInputStream responseInStream = new FileInputStream(new File(rootPath+responseFileName));
			bufferedReader = new BufferedReader(new InputStreamReader(responseInStream));
	        line = new String();
	        content = new StringBuilder();
	        while ((line = bufferedReader.readLine()) != null) {
	            content.append(line+"\r\n");
	        }
	        result.add(content.toString());
	        responseInStream.close();
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bufferedReader.close();
		}
		return result;
	}
	
}
