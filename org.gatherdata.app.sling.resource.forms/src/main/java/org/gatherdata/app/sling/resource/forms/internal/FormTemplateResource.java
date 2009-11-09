package org.gatherdata.app.sling.resource.forms.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.forms.core.model.FormTemplate;
import org.joda.time.DateTime;

public class FormTemplateResource extends SyntheticResource implements Resource, FormTemplate {

    public final static String RESOURCE_FAMILY_TYPE = "gather";
    
    public final static String RESOURCE_MEMBER_TYPE = "form";

    public static final String RESOURCE_TYPE = RESOURCE_FAMILY_TYPE + "/" + RESOURCE_MEMBER_TYPE;
    
    
    private FormTemplate formTemplate;

    private URI requestedUid;

    public FormTemplateResource(ResourceResolver resourceResolver, String path, FormTemplate formTemplate) {
        super(resourceResolver, path, RESOURCE_TYPE);
        this.formTemplate = formTemplate;
    }
    
    public FormTemplateResource(ResourceResolver resourceResolver, String path, URI requestedUid) {
        super(resourceResolver, path, RESOURCE_TYPE);
        setUid(requestedUid);
    }

    public FormTemplate getForm() {
        return formTemplate;
    }
    
    public URI getUid() {
        URI uid = null;
        if (formTemplate != null) {
            return formTemplate.getUid();
        } else if (requestedUid != null) {
            uid = requestedUid;
        }
        return uid;
    }

    public void setUid(URI requestedUid) {
        this.requestedUid = requestedUid;
    }
    
    @Override
    public String toString() {
        return "FormTemplate [form=" + formTemplate + "]";
    }
    
    public boolean exists() {
        return formTemplate != null;
    }

    public DateTime getDateModified() {
        return formTemplate.getDateModified();
    }

    public String getFormStyle() {
        return formTemplate.getFormStyle();
    }

    public String getFormTemplate() {
        return formTemplate.getFormTemplate();
    }

    public String getFormType() {
        return formTemplate.getFormType();
    }

    public String getDescription() {
        return formTemplate.getDescription();
    }

    public String getName() {
        return formTemplate.getName();
    }

    public DateTime getDateCreated() {
        return formTemplate.getDateCreated();
    }

    public URI selfIdentify() {
        return formTemplate.selfIdentify();
    }
    
    @Override
    public <Type> Type adaptTo(Class<Type> type) {
        if (type.equals(FormTemplateResource.class)) {
            return (Type) this;
        }
        return null;
    }
    
}
