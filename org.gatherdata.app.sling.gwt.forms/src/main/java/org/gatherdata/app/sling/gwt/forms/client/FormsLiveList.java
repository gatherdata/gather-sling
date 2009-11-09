package org.gatherdata.app.sling.gwt.forms.client;

import org.gatherdata.app.sling.gwt.forms.client.data.FormsXmlDataSource;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.ui.Anchor;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FormsLiveList extends VLayout {
    
    public FormsLiveList(DataSource dataSource) {

        setLayoutMargin(10);
        setWidth(940);
        
        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth100();
        listGrid.setHeight(400);
        listGrid.setAutoFetchData(true);
        listGrid.setDataSource(dataSource);

        addMember(listGrid);
        
        HLayout hLayout = new HLayout(15);
        IButton removeButton = new IButton("Remove");
        removeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ListGridRecord[] selectedRecords = listGrid.getSelection();
                for (ListGridRecord rec : selectedRecords) {
                    listGrid.removeData(rec);
                }
            }
        });
        hLayout.addMember(removeButton);

        IButton refreshButton = new IButton("Refresh");
        refreshButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.invalidateCache();
            }
        });
        hLayout.addMember(refreshButton);
        addMember(hLayout);
    }
    
    public final native String getOrbeonUrl()    /*-{ return $wnd.orbeonUrl; }-*/;
    
}
