package org.gatherdata.app.sling.gwt.alert.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ActionPlanLiveList extends VLayout {

    public ActionPlanLiveList(DataSource dataSource) {

        setWidth(940);
        setPadding(10);

        final DynamicForm entityForm = new DynamicForm();
        entityForm.setIsGroup(true);
        entityForm.setGroupTitle("Details");
        entityForm.setNumCols(4);
        entityForm.setDataSource(dataSource);

        StaticTextItem planUid = new StaticTextItem("uid");
        planUid.setTitle("UID");
        planUid.setDataPath("uid");
        
        StaticTextItem dateCreated = new StaticTextItem("dateCreated");
        dateCreated.setTitle("Date Created");
        
        TextItem eventName = new TextItem("name");
        eventName.setTitle("Name");
        eventName.setWidth("*");  

        TextAreaItem eventDescription = new TextAreaItem("description");
        eventDescription.setTitle("Description");
        eventDescription.setColSpan(3);
        eventDescription.setWidth("*");  
        eventDescription.setHeight("*");  

        ComboBoxItem ruleLanguage = new ComboBoxItem("ruleLanguage");
        ruleLanguage.setTitle("Language");
        ruleLanguage.setValueMap("xpath", "js", "simple", "constant");

        TextAreaItem ruleScript = new TextAreaItem("ruleScript");
        ruleScript.setTitle("Script");
        ruleScript.setColSpan(3);
        ruleScript.setWidth("*");  
        ruleScript.setHeight("*");  

        SectionItem ruleSection = new SectionItem();  
        ruleSection.setDefaultValue("Detection Rule");  
        ruleSection.setSectionExpanded(true);  
        ruleSection.setItemIds("ruleLanguage", "ruleScript");
        
        TextItem noticeDestination = new TextItem("noticeDestination");
        noticeDestination.setTitle("Destination URL");
        noticeDestination.setWidth("*");  
        
        ComboBoxItem noticeLanguage = new ComboBoxItem("noticeLanguage");
        noticeLanguage.setTitle("Language");
        noticeLanguage.setValueMap("vm");

        TextAreaItem noticeTemplate = new TextAreaItem("noticeTemplate");
        noticeTemplate.setTitle("Template");
        noticeTemplate.setColSpan(3);
        noticeTemplate.setWidth("*");  
        noticeTemplate.setHeight("*");  

        SectionItem noticeSection = new SectionItem();  
        noticeSection.setDefaultValue("Notification");  
        noticeSection.setSectionExpanded(true);  
        noticeSection.setItemIds("noticeDestination", "noticeLanguage", "noticeTemplate");  
        
        entityForm.setItems(planUid, dateCreated, 
                eventName, new SpacerItem(),
                eventDescription,
                ruleSection, ruleLanguage, ruleScript,
                noticeSection, noticeDestination, noticeLanguage,
                noticeTemplate
                );
        
        final ListGrid entityGrid = new ListGrid();
        entityGrid.setWidth100();
        entityGrid.setHeight(200);
        entityGrid.setDataSource(dataSource);
        entityGrid.setAutoFetchData(true);
        entityGrid.setCanEdit(false);
        entityGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                entityForm.reset();
                entityForm.editSelectedData(entityGrid);
            }
        });

        addMember(entityGrid);
        addMember(entityForm);

        HLayout buttonLayout = new HLayout();

        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                entityForm.saveData();
            }
        });
        buttonLayout.addMember(saveButton);

        IButton deleteButton = new IButton("Delete");
        deleteButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ListGridRecord[] selectedRecords = entityGrid.getSelection();
                for (ListGridRecord rec : selectedRecords) {
                    entityGrid.removeData(rec);
                }
            }
        });
        buttonLayout.addMember(deleteButton);

        IButton newButton = new IButton("New");
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                
                Map<String, Object> initialValues = new HashMap<String, Object>();
                entityForm.editNewRecord(initialValues);
//                entityGrid.startEditingNew();  
//                ListGridRecord rec = new ListGridRecord();
//                rec.setAttribute("dateCreated", new Date());
//                entityGrid.addData(rec);
            }
        });
        buttonLayout.addMember(newButton);

        addMember(buttonLayout);
    }
}
