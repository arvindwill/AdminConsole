package com.glenwood.glaceemr.gateway.utils;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.glenwood.glaceemr.utils.DataBaseUtils;

public class GatewayLogDAO {

	public ArrayList<GatewayLog> getLog(DataBaseUtils dbUtils, String fromDate, String toDate, int offSet, int limit)throws Exception{
		ArrayList<GatewayLog> logs = new ArrayList<GatewayLog>();
		try{
			String qry = "select date_format(gateway_transaction_log_date_time,'%m/%d/%Y %H:%i:%s') as timestamp,gateway_transaction_log_endpoint as endpoint,gateway_transaction_log_account_id as account_id,gateway_transaction_log_user_id as user_id,gateway_transaction_log_request_ip_address as ip_address,gateway_transaction_log_request_file_name as request_filename,gateway_transaction_log_response_file_name as response_filename  from gateway_transaction_log where cast(gateway_transaction_log_date_time as date) between  str_to_date('"+fromDate+"','%m/%d/%Y') and str_to_date('"+toDate+"','%m/%d/%Y') order by timestamp desc limit "+limit+" offset "+offSet;
			ResultSet rst = dbUtils.getStatement().executeQuery(qry);
			while(rst.next()){
				GatewayLog log = new GatewayLog();
				log.setTimeStamp(rst.getString("timestamp"));
				log.setEndPoint(rst.getString("endpoint"));
				log.setRequestIP(rst.getString("ip_address"));
				log.setAccountId(rst.getString("account_id"));
				log.setUserId(rst.getString("user_id"));
				log.setRequestFileName(rst.getString("request_filename"));
				log.setResponseFileName(rst.getString("response_filename"));
				logs.add(log);
			}
		}catch(Exception e){
			throw e;
		}
		return logs;
	}
	
	public int getLogCount(DataBaseUtils dbUtils, String fromDate, String toDate)throws Exception{
		int count = 0;
		try{
			String qry = "select count(*) as count from gateway_transaction_log where cast(gateway_transaction_log_date_time as date) between  str_to_date('"+fromDate+"','%m/%d/%Y') and str_to_date('"+toDate+"','%m/%d/%Y')";
			ResultSet rst = dbUtils.getStatement().executeQuery(qry);
			while(rst.next()){
				count = Integer.parseInt(rst.getString("count"));
			}
		}catch(Exception e){
			throw e;
		}
		return count;
	}
	
}
