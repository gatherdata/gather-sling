package org.gatherdata.app.sling.resource.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ResourceResolver;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.gatherdata.commons.model.DescribedEntity;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.spi.StorageService;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Base class for ResourceProviders which provide Gather UniqueEntities
 * retrieved from a StorageService. 
 * 
 */
public abstract class EntityResourceProvider implements ResourceProvider {

    private static Log log = LogFactory.getLog(EntityResourceProvider.class);

    /**
     * /<resource-family>/<resource-provider>/<resource-member-type>.<return-format-extension>
     * 
     */
    private final Pattern resourceApiPattern;

    /**
     * /<resource-family>/<resource-provider>/<resource-member-type>/<member-uid>.<return-format-extension>
     * /<resource-family>/<resource-provider>/<resource-member-type>/<member-uid>/<resource-member>.<return-format-extension>
     * 
     */
    private final Pattern resourceMemberPattern;

    public EntityResourceProvider(String providerRoot) {
        resourceApiPattern = Pattern.compile("^" + providerRoot + "(\\w*)$");
        resourceMemberPattern = Pattern.compile("^" + providerRoot
                + "(\\w*)/([\\S&&[^/]]*)$");
    }

    public abstract Class<? extends StorageService<?>> getServiceClass();
    public abstract Class<? extends UniqueEntity> getEntityClass(String alias);
    public abstract Resource createMemberResource(ResourceResolver resourceResolver, String fullPath, String memberType,
            String memberUid);
    
    public Pattern getResourceApiPattern() {
        return resourceApiPattern;
    }
    
    public Pattern getResourceMemberPattern() {
        return resourceMemberPattern;
    }
    
    public Resource getResource(ResourceResolver resourceResolver, HttpServletRequest request, String path) {
        return getResource(resourceResolver, path);
    }

    public Resource getResource(ResourceResolver resourceResolver, String path) {

        Resource foundResource = null;

        Matcher apiMatcher = resourceApiPattern.matcher(path);
        if (apiMatcher.matches()) {
            String requestedMemberType = apiMatcher.group(1);
            foundResource = createApiResource(resourceResolver, path, requestedMemberType);
        } else {

            Matcher urlMatcher = resourceMemberPattern.matcher(path);
            if (urlMatcher.matches()) {
                String requestedMemberType = urlMatcher.group(1);
                String requestedMemberUid = urlMatcher.group(2);

                foundResource = createMemberResource(resourceResolver, path, requestedMemberType, requestedMemberUid);
                log.debug("found resource: " + foundResource);
            }
        }
        return foundResource;
    }

    public Iterator<Resource> listChildren(Resource parent) {
        log.debug("listChildren(" + parent.getPath() + ")");
        return null;
    }

    public Resource createApiResource(ResourceResolver resourceResolver, String fullPath, String memberType) {
        SyntheticMapResource apiResource = null;

        apiResource = new SyntheticMapResource(resourceResolver, fullPath, "gather/api");
        apiResource.put("serviceClass", getServiceClass().getName());
        apiResource.put("entityClass", getEntityClass(memberType).getName());

        return apiResource;
    }
}
