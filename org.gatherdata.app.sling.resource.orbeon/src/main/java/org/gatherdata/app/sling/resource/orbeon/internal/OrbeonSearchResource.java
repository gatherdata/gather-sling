package org.gatherdata.app.sling.resource.orbeon.internal;

import java.net.URI;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;

public class OrbeonSearchResource extends SyntheticResource implements Resource {

    public static final String RESOURCE_TYPE = "orbeon/search";

    private URI requestedUid;

    public OrbeonSearchResource(ResourceResolver resourceResolver, String path, URI requestedUid) {
        super(resourceResolver, path, RESOURCE_TYPE);
    }
    
    public URI getUid() {
        return requestedUid;
    }


}
