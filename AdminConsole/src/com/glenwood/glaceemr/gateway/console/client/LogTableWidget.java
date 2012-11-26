package com.glenwood.glaceemr.gateway.console.client;

import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

public class LogTableWidget {
	
	public CellTable<GatewayLog> getTableObject(List<GatewayLog> data){
		final CellTable<GatewayLog> table = new CellTable<GatewayLog>();
        
    	TextColumn<GatewayLog> timeStampIDColumn = new TextColumn<GatewayLog>() {
            @Override
            public String getValue(GatewayLog object) {
              return object.getTimeStamp();
            }
        };
        
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
        
        table.addColumn(timeStampIDColumn,"Timestamp");
        table.addColumn(endPointColumn, "Endpoint");
        table.addColumn(acctIDColumn,"Account Id");
        table.addColumn(userIDColumn,"User Id");
        table.addColumn(IPAddressColumn,"IP Address");
        
        table.setRowCount(data.size());
    	final ListDataProvider<GatewayLog> dataProvider = new ListDataProvider<GatewayLog>(data);
    	dataProvider.addDataDisplay(table);
    	
    	final SingleSelectionModel<GatewayLog> selectionModel = new SingleSelectionModel<GatewayLog>();
    	
    	table.setSelectionModel(selectionModel);
    	
        
        table.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GatewayLogServiceAsync service = (GatewayLogServiceAsync)GWT.create(GatewayLogService.class);
				
				AsyncCallback<String> callBack = new AsyncCallback<String>(){
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                    	Window.alert(result);
                    }
                };
                try {
                	
                	GatewayLog selectedLog = selectionModel.getSelectedObject();
                	if(selectedLog!=null){
                		Window.alert(selectedLog.getTimeStamp());
                		service.getGatewayXMLContent("", callBack);
                	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, ClickEvent.getType());
        
        return table;
	}
}
