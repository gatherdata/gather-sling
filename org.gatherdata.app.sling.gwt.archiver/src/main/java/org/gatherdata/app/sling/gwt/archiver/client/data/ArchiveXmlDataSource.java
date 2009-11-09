package org.gatherdata.app.sling.gwt.archiver.client.data;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;

public class ArchiveXmlDataSource extends RestDataSource {
    
    public static final String ARCHIVE_SERVICE_ROOT = "/gather/archiver/";
    public static final String ARCHIVE_RESOURCE_ROOT = ARCHIVE_SERVICE_ROOT + "archive/";
    
    public static final String FETCH_LIST_URL = ARCHIVE_SERVICE_ROOT + "archive.xml";
    public static final String REMOVE_ITEM_URL = ARCHIVE_SERVICE_ROOT + "archive.xml";

    private static ArchiveXmlDataSource instance = null;  
    
    public static ArchiveXmlDataSource getInstance() {  
        if (instance == null) {  
            instance = new ArchiveXmlDataSource("archive", "Archive");  
        }  
        return instance;  
    }  
    
    public ArchiveXmlDataSource(String id, String title) {
        setID(id);
        setTitle(title);
        
        OperationBinding fetch = new OperationBinding();
        fetch.setOperationType(DSOperationType.FETCH);
        fetch.setDataProtocol(DSProtocol.GETPARAMS);
        OperationBinding add = new OperationBinding();
        add.setOperationType(DSOperationType.ADD);
        add.setDataProtocol(DSProtocol.POSTMESSAGE);
        OperationBinding update = new OperationBinding();
        update.setOperationType(DSOperationType.UPDATE);
        update.setDataProtocol(DSProtocol.POSTMESSAGE);
        OperationBinding remove = new OperationBinding();
        remove.setOperationType(DSOperationType.REMOVE);
        remove.setDataProtocol(DSProtocol.POSTMESSAGE);
        setOperationBindings(fetch, add, update, remove);

        DataSourceTextField uid = new DataSourceTextField("uid", "UID");
        uid.setPrimaryKey(true);
        uid.setCanEdit(false);
        
        DataSourceDateTimeField dateCreated = new DataSourceDateTimeField("dateCreated", "Date Created");
        dateCreated.setCanEdit(false);
        
        setFields(uid, dateCreated);
        setFetchDataURL(FETCH_LIST_URL);
        //setAddDataURL("data/dataIntegration/xml/responses/country_add_rest.xml");
        //setUpdateDataURL("data/dataIntegration/xml/responses/country_update_rest.xml");
        setRemoveDataURL(REMOVE_ITEM_URL);

    }  

    protected Object transformRequest(DSRequest dsRequest) {
        GWT.log("transformRequest(" + dsRequest.toString() + ")", null);
        return super.transformRequest(dsRequest);  
    }  

    protected void transformResponse(DSResponse response, DSRequest request, Object data) {
        GWT.log("transformResponse(" + response.toString() + "," + request.toString() + "," + data +")", null);  
        super.transformResponse(response, request, data);  
    }  

}
