package org.gatherdata.app.sling.resource.alert.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.gatherdata.alert.core.model.ActionPlan;
import org.gatherdata.alert.core.model.PlannedNotification;
import org.gatherdata.alert.core.model.RuleSet;
import org.gatherdata.commons.model.UniqueEntity;
import org.joda.time.DateTime;

public class ActionPlanResource extends SyntheticResource implements Resource, ActionPlan {
    
    public static final String RESOURCE_TYPE = "gather/actionPlan";
    
    /**
     * 
     */
    private static final long serialVersionUID = -8207883155398632760L;
    
    private ActionPlan innerModel;
    
    public ActionPlanResource(ResourceResolver resourceResolver, String path, ActionPlan innerModel) {
        super(resourceResolver, path, RESOURCE_TYPE);
        
        this.innerModel = innerModel;
    }

    public URI getUid() {
        return innerModel.getUid();
    }
    
    public DateTime getDateCreated() {
        return innerModel.getDateCreated();
    }

    public String getName() {
        return innerModel.getName();
    }

    public String getDescription() {
        return innerModel.getDescription();
    }

    public Iterable<? extends PlannedNotification> getNotifications() {
        return innerModel.getNotifications();
    }

    public RuleSet getRuleSet() {
        return innerModel.getRuleSet();
    }

    public URI selfIdentify() {
        return innerModel.selfIdentify();
    }
    
}
