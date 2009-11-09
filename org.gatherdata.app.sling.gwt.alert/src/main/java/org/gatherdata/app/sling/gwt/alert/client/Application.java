package org.gatherdata.app.sling.gwt.alert.client;

import org.gatherdata.app.sling.gwt.alert.client.data.ActionPlanDataSource;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        DataSource dataSource = ActionPlanDataSource.getInstance();  
  
        Widget mainWidget = new ActionPlanLiveList(dataSource);
        //Widget mainWidget = new CompoundEditor(dataSource);
        
        RootPanel.get("gwtApplication").add(mainWidget);
    }
}
