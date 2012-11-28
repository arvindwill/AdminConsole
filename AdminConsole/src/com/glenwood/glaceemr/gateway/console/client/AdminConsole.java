package com.glenwood.glaceemr.gateway.console.client;

import java.util.Date;
import java.util.List;

import com.glenwood.glaceemr.gateway.console.shared.GatewayLog;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
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
    FlowPanel requestXMLPanel = new FlowPanel();
    FlowPanel responseXMLPanel = new FlowPanel();
    TabLayoutPanel requestDetailPanel = new TabLayoutPanel(1,Unit.CM);
    TabLayoutPanel responseDetailPanel = new TabLayoutPanel(1,Unit.CM);
    final VerticalPanel vPanel = new VerticalPanel();
    final GatewayLogServiceAsync service = (GatewayLogServiceAsync)GWT.create(GatewayLogService.class);
    FlowPanel criteriaPanel = new FlowPanel();
    HorizontalPanel datePanel = new HorizontalPanel();

    
    public AdminConsole() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    public void onModuleLoad() {

        vPanel.setStylePrimaryName("mainPanel");

        criteriaPanel.setStylePrimaryName("sectionPanel");

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
        
        final FlowPanel logPanel = new FlowPanel();
        final LogTableWidget table = new LogTableWidget();
        table.setVisibleRange(0, limit);
        final Label noResultLabel = new Label("No transactions between the selected period");
        noResultLabel.setVisible(false);

        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		final SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setPageSize(limit);
		pager.setDisplay(table);

        logPanel.add(table);
        logPanel.add(noResultLabel);
        logPanel.add(pager);
        logPanel.setVisible(false);
        
        vPanel.add(logPanel);

        requestDetailPanel.setHeight("400 px");
        requestDetailPanel.add(new ScrollPanel(requestXMLPanel), "XML");
        requestDetailPanel.selectTab(0);
        
        responseDetailPanel.setHeight("400 px");
        responseDetailPanel.add(new ScrollPanel(responseXMLPanel), "XML");
        responseDetailPanel.selectTab(0);
        
        final HorizontalPanel detailPanel = new HorizontalPanel();
        detailPanel.setSize("1200 px", "200 px");
        detailPanel.setSpacing(3);
        
        VerticalPanel requestPanel = new VerticalPanel();
        requestPanel.setWidth("600 px");
        requestPanel.add(new HTML("<B>Request</B>"));
        requestPanel.add(requestDetailPanel);
        
        VerticalPanel responsePanel = new VerticalPanel();
        responsePanel.setWidth("600 px");
        responsePanel.add(new HTML("<B>Response</B>"));
        responsePanel.add(responseDetailPanel);

        detailPanel.add(requestPanel);
        detailPanel.add(responsePanel);
        
        detailPanel.setVisible(false);
        
        vPanel.add(detailPanel);        


        table.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				detailPanel.setVisible(false);
				Range range = event.getNewRange();
				table.getList(service, fromDate.getFormat().format(fromDate, fromDate.getValue()), toDate.getFormat().format(toDate, toDate.getValue()), range.getStart(), range.getLength());
			}
		});
        
        
        final SingleSelectionModel<GatewayLog> selectionModel = new SingleSelectionModel<GatewayLog>();
    	selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				GatewayLog selectedLog = selectionModel.getSelectedObject();
				try {
					detailPanel.setVisible(true);
					getXMLContent(service, selectedLog.getRequestFileName(),selectedLog.getResponseFileName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	table.setSelectionModel(selectionModel);

        search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	table.setVisibleRange(0, limit);
                AsyncCallback<Integer> callBack = new AsyncCallback<Integer>(){
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Integer count) {
                    	if(count>0){
                    		table.setVisible(true);
                    		pager.setVisible(true);
                    		logPanel.setVisible(true);
                    		detailPanel.setVisible(false);
                    		noResultLabel.setVisible(false);
                    		table.setRowCount(count);
                    		table.getList(service, fromDate.getFormat().format(fromDate, fromDate.getValue()), toDate.getFormat().format(toDate, toDate.getValue()), 0, limit);
                    	}else{
                    		detailPanel.setVisible(false);
                    		table.setVisible(false);
                    		pager.setVisible(false);
                    		noResultLabel.setVisible(true);
                    		vPanel.add(noResultLabel);
                    	}
                    }
                };
                service.getInitialCount(fromDate.getFormat().format(fromDate, fromDate.getValue()), toDate.getFormat().format(toDate, toDate.getValue()),callBack);
            }
        });
        
        RootPanel rootPanel = RootPanel.get();
        rootPanel.add(vPanel);
    }
    
	
    public void getXMLContent(GatewayLogServiceAsync service, String requestFileName, String responseFileName) throws Exception{
		AsyncCallback<List<String>> callBack = new AsyncCallback<List<String>>(){
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(List<String> result) {
            	requestXMLPanel.clear();
            	requestXMLPanel.add(new Label(result.get(0)));
            	responseXMLPanel.clear();
            	responseXMLPanel.add(new Label(result.get(1)));
            }
        };
        service.getGatewayXMLContent(requestFileName,responseFileName,callBack);
	}
}
