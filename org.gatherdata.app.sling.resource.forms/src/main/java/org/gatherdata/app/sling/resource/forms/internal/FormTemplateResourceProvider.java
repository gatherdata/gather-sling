package org.gatherdata.app.sling.resource.forms.internal;

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
import org.gatherdata.app.sling.resource.forms.spi.ResourceMemberFactory;
import org.gatherdata.app.sling.resource.core.EntityResourceProvider;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.spi.StorageService;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.spi.FormCatalogService;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Provides FormTemplate entities as resources identified by uid.
 * 
 */
public class FormTemplateResourceProvider extends EntityResourceProvider implements ResourceProvider {

    private static Log log = LogFactory.getLog(FormTemplateResourceProvider.class);

    /**
     * /resource-family-type/<resource-provider>
     */
    public static final String PROVIDER_ROOT = "/gather/forms/";

    public static final String RESOURCE_TYPES = FormTemplateResource.RESOURCE_TYPE;

    @Inject
    FormCatalogService entityService;

    public FormTemplateResourceProvider() {
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
                FormTemplate requestedEntity = entityService.get(requestedUid);
                if (requestedEntity != null) {
                    foundResource = new FormTemplateResource(resourceResolver, fullPath, requestedEntity);
                    log.debug("found resource: " + foundResource);
                } else {
                    log.debug("form not found");
                }
            }
        }

        return foundResource;
    }

    @Override
    public Class<? extends UniqueEntity> getEntityClass(String alias) {
        return FormTemplate.class;
    }

    @Override
    public Class<? extends StorageService<?>> getServiceClass() {
        return FormCatalogService.class;
    }

}
