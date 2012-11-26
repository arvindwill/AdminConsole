package com.glenwood.glaceemr.gateway.console.client;

import java.util.Date;
import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AdminConsole implements EntryPoint {


    /**
     * Creates a new instance of consoleEntryPoint
     */
    
    DateBox fromDate = new DateBox();
    DateBox toDate = new DateBox();
    int limit = 10;
    int offset = 0;
    
    public AdminConsole() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    public void onModuleLoad() {

        final VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStylePrimaryName("mainPanel");

        FlowPanel criteriaPanel = new FlowPanel();
        criteriaPanel.setStylePrimaryName("sectionPanel");


        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
        datePanel.setSpacing(10);
        
        fromDate.setValue(new Date());
        fromDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));

        
        toDate.setValue(new Date());
        toDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        
        ListBox endPoint = new ListBox();
        endPoint.addItem("HIE");
        
        TextBox accountId = new TextBox();

        datePanel.add(new HTML("Date Range:"));
        datePanel.add(fromDate);
        datePanel.add(new HTML("to"));
        datePanel.add(toDate);
        datePanel.add(new HTML("Endpoint:"));
        datePanel.add(endPoint);
        datePanel.add(new HTML("Account ID:"));
        datePanel.add(accountId);

        HorizontalPanel endPointPanel = new HorizontalPanel();
        endPointPanel.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
        endPointPanel.setSpacing(5);
        Button search = new SubmitButton();
        search.setText("Search");
        endPointPanel.add(search);
        
        criteriaPanel.add(datePanel);
        criteriaPanel.add(endPointPanel);

        vPanel.add(criteriaPanel);
        
        search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                GatewayLogServiceAsync service = (GatewayLogServiceAsync)GWT.create(GatewayLogService.class);
                
                AsyncCallback<List<GatewayLog>> callBack = new AsyncCallback<List<GatewayLog>>(){
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(List<GatewayLog> resultObj) {
                    	LogTableWidget tableWidget = new LogTableWidget();
                        final CellTable<GatewayLog> table = tableWidget.getTableObject(resultObj);
                        table.setVisible(false);
                        vPanel.add(table);

                    	

                    	table.setVisible(true);
                    }
                };
                service.getList(fromDate.getFormat().format(fromDate, fromDate.getValue()), toDate.getFormat().format(toDate, toDate.getValue()),0,10,callBack);
            }
        });
        
        HorizontalPanel detailPanel = new HorizontalPanel();

        vPanel.add(detailPanel);
        
        RootPanel rootPanel = RootPanel.get();
        rootPanel.add(vPanel);
    }
}
