package com.glenwood.glaceemr.gateway.console.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class DetailPanelWidget {
	public TabPanel getDetailPanel(String requestFileName){
        TabPanel requestDetail = new TabPanel();
        FlowPanel requestXML = new FlowPanel();
        FlowPanel requestHTML = new FlowPanel();
        requestDetail.add(requestHTML, "Detail");
        requestDetail.add(requestXML, "XML");
        return requestDetail;
	}
}
