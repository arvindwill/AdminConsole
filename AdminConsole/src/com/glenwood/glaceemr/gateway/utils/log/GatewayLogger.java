/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.utils.log;

import com.glenwood.glaceemr.utils.DataBaseUtils;
import com.glenwood.glaceemr.utils.LogFile;
import com.glenwood.glaceemr.utils.LogLevel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author gsivashanmugam
 */
public class GatewayLogger {
    public static long insertRequestLog(DataBaseUtils dbUtils, String endPoint, LogLevel level, String accountId, String userId, String ipAddress, String requestString, String rootPath)throws Exception{
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHMMss");
        String fileName = "/"+cal.get(Calendar.YEAR) +"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+formatter.format(cal.getTime())+"_request.txt";
        LogFile.writeToFile(rootPath + "/log" + fileName, requestString);
        String qryString = new String("");
        qryString = "insert into gateway_transaction_log(gateway_transaction_log_endpoint,gateway_transaction_log_level,gateway_transaction_log_account_id,gateway_transaction_log_user_id,gateway_transaction_log_request_file_name,gateway_transaction_log_request_ip_address) values('"+endPoint+"',"+level.intValue()+",'"+accountId+"','"+userId+"','"+fileName+"','"+ipAddress+"')";
        Statement stmt = dbUtils.getStatement();
        stmt.executeUpdate(qryString);
        ResultSet resultSet = stmt.executeQuery("select LAST_INSERT_ID()");
        long primaryId = -1;
        if(resultSet.first()){
            primaryId = resultSet.getLong(1);
        }
        return primaryId;
    }
    
    public static void updateResponseLog(DataBaseUtils dbUtils, long transactionId, LogLevel level, String responseString, String rootPath)throws Exception{
        String qryString = new String();
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHMMss");
        String fileName = "/"+cal.get(Calendar.YEAR) +"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+formatter.format(cal.getTime())+"_response.txt";
        LogFile.writeToFile(rootPath + "/log" + fileName, responseString);
        qryString = "update gateway_transaction_log set gateway_transaction_log_level="+level.intValue()+",gateway_transaction_log_response_file_name='"+fileName+"' where gateway_transaction_log_id="+transactionId;
        dbUtils.getStatement().executeUpdate(qryString);
    }
    
    
    public static void getLogDetails(DataBaseUtils dbUtils, String endPoint, String accountId, String fromDate, String endDate)throws Exception{
    }
    
    
    
    
    
}
