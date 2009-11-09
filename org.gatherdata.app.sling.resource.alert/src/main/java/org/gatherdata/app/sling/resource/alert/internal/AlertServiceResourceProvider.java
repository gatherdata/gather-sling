package org.gatherdata.app.sling.resource.alert.internal;

import java.net.URI;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ResourceResolver;
import org.gatherdata.alert.core.model.ActionPlan;
import org.gatherdata.alert.core.spi.AlertService;
import org.gatherdata.app.sling.resource.core.EntityResourceProvider;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.spi.StorageService;

import com.google.inject.Inject;

/**
 * Provides AlertService related entities as resources.
 *
 */
public class AlertServiceResourceProvider extends EntityResourceProvider implements ResourceProvider {


    private static Log log = LogFactory.getLog(AlertServiceResourceProvider.class);

    public static final String PROVIDER_ROOT = "/gather/alert/";
    
    public static final String RESOURCE_TYPES = ActionPlanResource.RESOURCE_TYPE;

    @Inject
    AlertService alertService;


    public AlertServiceResourceProvider() {
        super(PROVIDER_ROOT);
    }


    @Override
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
                ActionPlan requestedEntity = alertService.get(requestedUid);
                if (requestedEntity != null) {
                    foundResource = new ActionPlanResource(resourceResolver, fullPath, requestedEntity);
                    log.debug("found resource: " + foundResource);
                } else {
                    log.debug("entity not found");
                }
            }
        }

        return foundResource;
    }


    @Override
    public Class<? extends UniqueEntity> getEntityClass(String alias) {
        return ActionPlan.class;
    }


    @Override
    public Class<? extends StorageService<?>> getServiceClass() {
        return AlertService.class;
    }
}
