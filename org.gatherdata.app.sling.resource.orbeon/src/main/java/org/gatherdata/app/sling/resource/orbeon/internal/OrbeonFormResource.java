package org.gatherdata.app.sling.resource.orbeon.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.forms.core.model.FormTemplate;

public class OrbeonFormResource extends SyntheticResource implements Resource {
    
    public static final String RESOURCE_TYPE = "orbeon/form";

    // ABKTODO: push these header constants down into an interface or enum
    public static final String FORM_STYLE_HEADER = "FormStyle";
    
    private FormTemplate formTemplate;

    private URI requestedUid;

    public OrbeonFormResource(ResourceResolver resourceResolver, String path, FormTemplate formTemplate) {
        super(resourceResolver, path, RESOURCE_TYPE);
        this.formTemplate = formTemplate;
    }
    
    public OrbeonFormResource(ResourceResolver resourceResolver, String path, URI requestedUid) {
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
        return "OrbeonResource [form=" + formTemplate + "]";
    }
    
    public boolean exists() {
        return formTemplate != null;
    }

    @Override
    public <Type> Type adaptTo(Class<Type> type) {
        if (type.equals(OrbeonFormResource.class)) {
            return (Type) this;
        }
        return null;
    }
    

    
}
