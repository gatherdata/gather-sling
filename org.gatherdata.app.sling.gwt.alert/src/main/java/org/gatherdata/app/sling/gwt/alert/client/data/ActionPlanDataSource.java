package org.gatherdata.app.sling.gwt.alert.client.data;

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
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;

public class ActionPlanDataSource extends RestDataSource {
    
    public static final String ALERT_SERVICE_ROOT = "/gather/alert/";
    public static final String ALERT_RESOURCE_ROOT = ALERT_SERVICE_ROOT + "plan/";
    
    public static final String FETCH_LIST_URL = ALERT_SERVICE_ROOT + "plan.xml";
    public static final String REMOVE_ITEM_URL = ALERT_SERVICE_ROOT + "plan.xml";
    public static final String ADD_ITEM_URL = ALERT_SERVICE_ROOT + "plan.xml";
    public static final String UPDATE_ITEM_URL = ALERT_SERVICE_ROOT + "plan.xml";

    private static ActionPlanDataSource instance = null;  
    
    public static ActionPlanDataSource getInstance() {  
        if (instance == null) {  
            instance = new ActionPlanDataSource("plan", "Action Plan");  
        }  
        return instance;  
    }  
    
    public ActionPlanDataSource(String id, String title) {
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
        
        DataSourceTextField name = new DataSourceTextField("name", "Name");
        DataSourceTextField description = new DataSourceTextField("description", "Description");
        
        // rule section
        DataSourceTextField ruleUid = new DataSourceTextField("ruleUid", "Rule UID");
        ruleUid.setCanEdit(false);
        ruleUid.setHidden(true);
        DataSourceEnumField ruleLanguage = new DataSourceEnumField("ruleLanguage", "Rule Language");
        DataSourceTextField ruleScript = new DataSourceTextField("ruleScript", "Rule Script");
        
        // notification section noticeUid
        DataSourceTextField noticeUid = new DataSourceTextField("noticeUid", "Notification UID");
        noticeUid.setCanEdit(false);
        noticeUid.setHidden(true);
        DataSourceTextField noticeDestination = new DataSourceTextField("noticeDestination", "Destination");
        DataSourceEnumField noticeLanguage = new DataSourceEnumField("noticeLanguage", "Temlate Language");
        DataSourceTextField noticeTemplate = new DataSourceTextField("noticeTemplate", "Notification Temlate");
        
        setFields(uid, dateCreated, name, description,  
                ruleUid, ruleLanguage, ruleScript, 
                noticeDestination, noticeLanguage, noticeTemplate);
        
        setFetchDataURL(FETCH_LIST_URL);
        setAddDataURL(ADD_ITEM_URL);
        setUpdateDataURL(UPDATE_ITEM_URL);
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
