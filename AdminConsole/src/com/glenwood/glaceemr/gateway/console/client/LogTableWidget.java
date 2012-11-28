package com.glenwood.glaceemr.gateway.console.client;

import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LogTableWidget extends CellTable<GatewayLog> {

	public LogTableWidget(){
		super();
		initateTable();
	}
	
	private void initateTable(){
		TextColumn<GatewayLog> timeStampIDColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getTimeStamp();
            }
        };
        timeStampIDColumn.setSortable(true);
        
    	TextColumn<GatewayLog> endPointColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getEndPoint();
            }
        };
    	
    	TextColumn<GatewayLog> acctIDColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getAccountId();
            }
        };
        
    	TextColumn<GatewayLog> userIDColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getUserId();
            }
        };                        
    	TextColumn<GatewayLog> IPAddressColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getRequestIP();
            }
        }; 
        
        this.addColumn(timeStampIDColumn,"Timestamp");
        this.addColumn(endPointColumn, "Endpoint");
        this.addColumn(acctIDColumn,"Account Id");
        this.addColumn(userIDColumn,"User Id");
        this.addColumn(IPAddressColumn,"IP Address");
        
	}
	
	public void getList(GatewayLogServiceAsync service, String fromDate, String toDate, final int offSet, int limit){
		
		AsyncCallback<List<GatewayLog>> callBack = new AsyncCallback<List<GatewayLog>>(){
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
            
            @Override
            public void onSuccess(List<GatewayLog> resultObj) {
            	setRowData(offSet, resultObj);
            }
		};
		service.getList(fromDate, toDate, offSet, limit,callBack);
	}

	
	
}
