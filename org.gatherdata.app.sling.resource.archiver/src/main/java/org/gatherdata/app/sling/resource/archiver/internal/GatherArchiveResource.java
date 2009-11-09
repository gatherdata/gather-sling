package org.gatherdata.app.sling.resource.archiver.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.joda.time.DateTime;

public class GatherArchiveResource extends SyntheticResource implements Resource, GatherArchive {

    public final static String RESOURCE_FAMILY_TYPE = "gather";
    
    public final static String RESOURCE_MEMBER_TYPE = "archive";

    public static final String RESOURCE_TYPE = RESOURCE_FAMILY_TYPE + "/" + RESOURCE_MEMBER_TYPE;
    
    /**
     * 
     */
    private static final long serialVersionUID = -8207883155398632760L;
    
    private GatherArchive archive;
    
    public GatherArchiveResource(ResourceResolver resourceResolver, String path, GatherArchive archive) {
        super(resourceResolver, path, RESOURCE_TYPE);
        
        this.archive = archive;
    }

    public Serializable getContent() {
        return archive.getContent();
    }

    public DateTime getDateCreated() {
        return archive.getDateCreated();
    }

    public Map<String, String> getMetadata() {
        return archive.getMetadata();
    }

    public URI getUid() {
        return archive.getUid();
    }

    public URI selfIdentify() {
        return archive.selfIdentify();
    }
    
}
