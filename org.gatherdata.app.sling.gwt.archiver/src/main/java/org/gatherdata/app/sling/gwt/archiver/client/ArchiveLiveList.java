package org.gatherdata.app.sling.gwt.archiver.client;

import org.gatherdata.app.sling.gwt.archiver.client.data.ArchiveXmlDataSource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
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

public class ArchiveLiveList extends VLayout {

    public ArchiveLiveList(DataSource dataSource) {

        setLayoutMargin(10);
        setWidth(940);

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth100();
        listGrid.setHeight(400);
        listGrid.setAutoFetchData(true);
        listGrid.setDataSource(dataSource);

        addMember(listGrid);

        HLayout hLayout = new HLayout(15);

        final Button viewButton = new Button("View");
        viewButton.setWidth(150);
        viewButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ListGridRecord selectedRecord = listGrid.getSelectedRecord();
                final String uid = selectedRecord.getAttribute("uid");
                final String url = ArchiveXmlDataSource.ARCHIVE_RESOURCE_ROOT + uid + ".json";

                final Window winModal = new Window();
                winModal.setWidth("80%");
                winModal.setHeight("80%");
                winModal.setTitle("Archive: " + uid);
                winModal.setShowMinimizeButton(false);
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.centerInPage();
                winModal.addCloseClickHandler(new CloseClickHandler() {
                    public void onCloseClick(CloseClientEvent event) {
                        winModal.destroy();
                    }
                });

                ArchiveViewPanel archivePanel = new ArchiveViewPanel(url);

                winModal.addItem(archivePanel);
                winModal.show();

            }
        });
        hLayout.addMember(viewButton);

        Button removeButton = new Button("Remove");
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
}
