package com.glenwood.glaceemr.gateway.console.client;

import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

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
	
	public void updateTableData(List<GatewayLog> data){
        final ListDataProvider<GatewayLog> dataProvider = new ListDataProvider<GatewayLog>(data);
    	dataProvider.addDataDisplay(this);
	}
	

	
	
}
