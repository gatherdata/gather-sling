package org.gatherdata.app.sling.gwt.alert.client;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CompoundEditor extends HLayout {  
    private DataSource datasource;  
    private DynamicForm form;  
    private ListGrid grid;  
    private IButton saveButton;  

    public CompoundEditor(DataSource datasource) {  
        this.datasource = datasource;  
    }  

    protected void onInit() {  
        super.onInit();  
        this.form = new DynamicForm();  

        form.setDataSource(datasource);  

        saveButton = new IButton("Save");  
        saveButton.setLayoutAlign(Alignment.CENTER);  
        saveButton.addClickHandler(new ClickHandler() {  

            public void onClick(ClickEvent event) {  
                form.saveData();  
            }  
        });  

        VLayout editorLayout = new VLayout(5);  
        editorLayout.addMember(form);  
        editorLayout.addMember(saveButton);  
        editorLayout.setWidth(280);  

        grid = new ListGrid();  
        grid.setWidth(500);  
        grid.setHeight(350);  
        grid.setDataSource(datasource);  
        grid.setShowResizeBar(true);  
        grid.setAutoFetchData(true);  
        grid.addRecordClickHandler(new RecordClickHandler() {  
            public void onRecordClick(RecordClickEvent event) {  
                form.clearErrors(true);  
                form.editRecord(event.getRecord());  
                saveButton.enable();  
            }  

        });  

        addMember(grid);  
        addMember(editorLayout);  
    }  

    public DataSource getDatasource() {  
        return datasource;  
    }  

    public void setDatasource(DataSource datasource) {  
        this.datasource = datasource;  
        grid.setDataSource(datasource);  
        form.setDataSource(datasource);  
        saveButton.disable();  
        grid.fetchData();  
    }  
}
