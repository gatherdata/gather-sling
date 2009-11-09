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

public class OrbeonDataResource extends SyntheticResource implements Resource {
    
    public static final String RESOURCE_TYPE = "orbeon/data";

    private GatherArchive dataArchive;

    private URI requestedUid;

    public OrbeonDataResource(ResourceResolver resourceResolver, String path, GatherArchive dataArchive) {
        super(resourceResolver, path, RESOURCE_TYPE);
        this.dataArchive = dataArchive;
    }
    
    public OrbeonDataResource(ResourceResolver resourceResolver, String path, URI requestedUid) {
        super(resourceResolver, path, RESOURCE_TYPE);
        setUid(requestedUid);
    }

    public GatherArchive getArchive() {
        return dataArchive;
    }
    
    public URI getUid() {
        URI uid = null;
        if (dataArchive != null) {
            return dataArchive.getUid();
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
        return "OrbeonResource [data=" + dataArchive + "]";
    }
    
    public boolean exists() {
        return dataArchive != null;
    }

    @Override
    public <Type> Type adaptTo(Class<Type> type) {
        if (type.equals(OrbeonDataResource.class)) {
            return (Type) this;
        }
        return null;
    }
    

    
}
