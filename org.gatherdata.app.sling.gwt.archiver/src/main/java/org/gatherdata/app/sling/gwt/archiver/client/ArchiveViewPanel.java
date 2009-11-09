package org.gatherdata.app.sling.gwt.archiver.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BlurbItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class ArchiveViewPanel extends VLayout {

    private GatherArchiveOverlay model;

    public ArchiveViewPanel(String archiveUrl) {
        setLayoutMargin(10);
        setWidth100();
        setHeight100();
        
        fetchArchive(archiveUrl);
        
    }

    private void fetchArchive(String archiveUrl) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, archiveUrl);

        try {
          Request request = builder.sendRequest(null, new RequestCallback() {
            public void onError(Request request, Throwable exception) {
              displayError("Couldn't retrieve JSON");
            }

            public void onResponseReceived(Request request, Response response) {
              if (200 == response.getStatusCode()) {
                  displayArchive(asGatherArchive(response.getText()));
              } else {
                displayError("Couldn't retrieve JSON (" + response.getStatusText()
                    + ")");
              }
            }
          });
        } catch (RequestException e) {
          displayError("Couldn't retrieve JSON");
        }
        
    }
    
    private final native GatherArchiveOverlay asGatherArchive(String json) /*-{
      return eval('(' + json + ')');
    }-*/;
    
    private void displayArchive(GatherArchiveOverlay model) {
        this.model = model;
        
        DynamicForm form = new DynamicForm();
        
        StaticTextItem uidField = new StaticTextItem("uid", "UID");
        uidField.setValue(model.getUid());
        uidField.setWidth(300);
        
        StaticTextItem dateCreatedField = new StaticTextItem("dateCreated", "Date Created");
        dateCreatedField.setValue(model.getDateCreated());
        dateCreatedField.setWidth(300);
        
        
        form.setFields(uidField, dateCreatedField);
        addMember(form);
        
        HTMLPane contentPane = new HTMLPane();
        contentPane.setContents("<textarea rows=\"40\" cols=\"120\">" + model.getContent() + "</textarea>");
        contentPane.setPadding(4);
        addMember(contentPane);
    }
    
    private void displayError(String errorMessage) {
        Label errorLabel = new Label();
        errorLabel.setWidth100();
        errorLabel.setHeight100();
        errorLabel.setContents(errorMessage);
        addMember(errorLabel);
    }
    
}
