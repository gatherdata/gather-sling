package org.gatherdata.app.sling.resource.forms.spi;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public interface ResourceMemberFactory {

    Resource createResource(ResourceResolver resourceResolver, String fullPath, String memberUid);
    
    String getResourceMemberType();
    
}
