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
    public List<GatewayLog> getList(String fromDate, String toDate, int offSet, int limit) throws Exception {
    	DataBaseUtils dbUtils = null;
    	GatewayLogDAO logDAO = new GatewayLogDAO();
    	ArrayList<GatewayLog> list = new ArrayList<GatewayLog>();
    	try{
    		dbUtils = new DataBaseUtils(this.getServletContext().getInitParameter("connectionString"));
    		list = logDAO.getLog(dbUtils, fromDate, toDate, offSet, limit);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		dbUtils.destroy();
    	}
        return list;
    }

	@Override
	public Integer getInitialCount(String fromDate, String toDate)throws Exception {
		DataBaseUtils dbUtils = null;
    	GatewayLogDAO logDAO = new GatewayLogDAO();		
		int count = 0;
    	try{
    		dbUtils = new DataBaseUtils(this.getServletContext().getInitParameter("connectionString"));
    		count = logDAO.getLogCount(dbUtils, fromDate, toDate);
    		return new Integer(count);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		dbUtils.destroy();
    	}
    	return Integer.valueOf(count); 
	}
	
	
	public List<String> getGatewayXMLContent(String requestFileName, String responseFileName)throws Exception{
        StringBuilder content = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        FileInputStream requestInStream = null;
        FileInputStream responseInStream = null;
		try{
			String rootPath = this.getServletContext().getInitParameter("sharedPath")+"/log";
			
			requestInStream = new FileInputStream(new File(rootPath+requestFileName));
			bufferedReader = new BufferedReader(new InputStreamReader(requestInStream));
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            content.append(line+"\r\n");
	        }
	        result.add(content.toString());

			responseInStream = new FileInputStream(new File(rootPath+responseFileName));
			bufferedReader = new BufferedReader(new InputStreamReader(responseInStream));
	        line = new String();
	        content = new StringBuilder();
	        while ((line = bufferedReader.readLine()) != null) {
	            content.append(line+"\r\n");
	        }
	        result.add(content.toString());
	        
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			requestInStream.close();
			responseInStream.close();
			bufferedReader.close();
		}
		return result;
	}
	
}
