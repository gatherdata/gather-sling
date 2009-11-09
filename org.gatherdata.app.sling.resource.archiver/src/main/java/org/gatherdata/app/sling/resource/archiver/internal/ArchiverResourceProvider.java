package org.gatherdata.app.sling.resource.archiver.internal;

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
import org.gatherdata.app.sling.resource.archiver.spi.ResourceMemberFactory;
import org.gatherdata.app.sling.resource.core.EntityResourceProvider;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.spi.StorageService;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Provides GatherArchive entities as resources identified by uid.
 * 
 */
public class ArchiverResourceProvider extends EntityResourceProvider implements ResourceProvider {

    private static Log log = LogFactory.getLog(ArchiverResourceProvider.class);

    /**
     * /resource-family-type/<resource-provider>
     */
    public static final String PROVIDER_ROOT = "/gather/archiver/";

    public static final String RESOURCE_TYPES = GatherArchiveResource.RESOURCE_TYPE;

    @Inject
    ArchiverService archiverService;

    public ArchiverResourceProvider() {
        super(PROVIDER_ROOT);
    }

    public Resource createMemberResource(ResourceResolver resourceResolver, String fullPath, String memberType,
            String memberUid) {
        Resource foundResource = null;

        if (memberUid.length() > 0) {
            URI requestedUid = null;
            try {
                requestedUid = new URI(memberUid);
            } catch (Exception e) {
                log.warn("request for bad UID: " + memberUid);
            }
            if (requestedUid != null) {
                log.debug("looking for " + memberType + " with uid: " + requestedUid.toASCIIString());
                GatherArchive requestedArchive = archiverService.get(requestedUid);
                if (requestedArchive != null) {
                    foundResource = new GatherArchiveResource(resourceResolver, fullPath, requestedArchive);
                    log.debug("found resource: " + foundResource);
                } else {
                    log.debug("archive not found");
                }
            }
        }

        return foundResource;
    }

    @Override
    public Class<? extends UniqueEntity> getEntityClass(String alias) {
        return GatherArchive.class;
    }

    @Override
    public Class<? extends StorageService<?>> getServiceClass() {
        return ArchiverService.class;
    }

}
